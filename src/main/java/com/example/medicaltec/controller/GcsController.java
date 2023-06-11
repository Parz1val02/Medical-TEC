package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.UsuarioRepository;
import com.google.cloud.storage.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class GcsController {
    @Autowired
    final UsuarioRepository usuarioRepository;

    public GcsController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/guardarImagen")
    public String guardarImagenEvento(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/paciente/perfil";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/paciente/perfil";
        }
        if(checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/paciente/perfil";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPacientes/perfil-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/paciente/perfil";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/paciente/perfil";
        }
    }

    public static void uploadObject
            (MultipartFile multipartFile, String fileName, String projectId, String gcpBucketId) {
        try {
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));
            Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());
            bucket.create( fileName + ".jpeg", fileData);
        } catch (Exception e) {
//            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            throw new RuntimeException("An error occurred while storing data to GCS");
        }
    }

    private static File convertFile(MultipartFile file) {

        try {
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            return convertedFile;
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while converting the file");
        }
    }

    private static boolean checkFileExtension(String fileName) {
        boolean present = false;
        if (fileName != null && fileName.contains(".")) {
            String[] extensionList = {".jpeg",".jpg"};
            for (String extension : extensionList) {
                if (fileName.endsWith(extension)) {
//                    LOGGER.debug("Accepted file type : {}", extension);
                    return present;
                }
            }
        }
        return !present;
    }
    public static byte[] downloadObject
            (String projectId, String bucketName, String blobName) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, blobName);
        Blob blob = storage.get(blobId);
        return blob.getContent();
    }
    @GetMapping("/perfilPaciente")
    public ResponseEntity<byte[]> displayItemImage(HttpSession httpSession, HttpServletRequest httpServletRequest, Authentication authentication) throws IOException {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String id = usuario.getId();
        String blobName = "fotosPacientes/perfil-" + id +".jpeg";
        byte[] image = downloadObject("glowing-hearth-316315 ", "wenas", blobName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
