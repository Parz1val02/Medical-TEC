package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.Entity.UxUi;
import com.example.medicaltec.repository.UsuarioRepository;
import com.example.medicaltec.repository.UxUiRepository;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class GcsController {
    @Autowired
    final UsuarioRepository usuarioRepository;
    final UxUiRepository uxUiRepository;

    public GcsController(UsuarioRepository usuarioRepository, UxUiRepository uxUiRepository) {
        this.usuarioRepository = usuarioRepository;
        this.uxUiRepository = uxUiRepository;
    }

    @PostMapping("/uploadPaciente")
    public String guardarPerfilPaciente(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
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
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/paciente/perfil";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPerfil/perfil-" + id;
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

    @PostMapping("/uploadAdministrativo")
    public String guardarPerfilAdministrativo(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/administrativo/perfil";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/administrativo/perfil";
        }
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/administrativo/perfil";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPerfil/perfil-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/administrativo/perfil";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/administrativo/perfil";
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
                    return !present;
                }
            }
        }
        return present;
    }
    public static byte[] downloadObject
            (String projectId, String bucketName, String blobName) throws IOException {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, blobName);
        Blob blob = storage.get(blobId);
        return blob.getContent();
    }
    @GetMapping("/fotoPerfil")
    public ResponseEntity<byte[]> displayItemImage(HttpSession httpSession, HttpServletRequest httpServletRequest, Authentication authentication) throws IOException {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String id = usuario.getId();
        String blobName = "fotosPerfil/perfil-" + id +".jpeg";
        byte[] image = downloadObject("glowing-hearth-316315 ", "wenas", blobName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/fotoSede")
    public ResponseEntity<byte[]> displayItemImageSede(@RequestParam("idSede")String idSede) throws IOException {
        String blobName = "fotosSede/sede" + idSede +".jpeg";
        byte[] image = downloadObject("glowing-hearth-316315 ", "wenas", blobName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    @GetMapping("/fotoPerfilDoctor")
    public ResponseEntity<byte[]> displayItemImageS(@RequestParam ("dni") String dni) throws IOException {
        String blobName = "fotosPerfil/perfil-" + dni +".jpeg";
        byte[] image = downloadObject("glowing-hearth-316315 ", "wenas", blobName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    @PostMapping("/uploadFirma")
    public String guardarFirmDoctor(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/doctor/config";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/doctor/config";
        }
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/doctor/config";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosFirma/firma-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Firma actualizada de manera exitosa");
            return "redirect:/doctor/config";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar firma");
            return "redirect:/doctor/config";
        }
    }
    @GetMapping("/fotoFirmaDoctor")
    public ResponseEntity<byte[]> displayItemImageSS(@RequestParam ("dni") String dni) throws IOException {
        String blobName = "fotosFirma/firma-" + dni +".jpeg";
        byte[] image = downloadObject("glowing-hearth-316315 ", "wenas", blobName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    @PostMapping("/uploadSuper")
    public String guardarPerfilSuper(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/superAdmin/confSup";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/superAdmin/confSup";
        }
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/superAdmin/confSup";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPerfil/perfil-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/superAdmin/confSup";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/superAdmin/confSup";
        }
    }
    @PostMapping("/uploadAdministrador")
    public String guardarPerfilAdministrador(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/administrador/settings";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/administrador/settings";
        }
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/administrador/settings";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPerfil/perfil-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/administrador/settings";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/administrador/settings";
        }
    }
    @PostMapping("/uploadDoctor")
    public String guardarPerfilDoctor(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/doctor/config";
        }
        if(file.getOriginalFilename().contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/doctor/config";
        }
        if(!checkFileExtension(file.getOriginalFilename())){
            attr.addFlashAttribute("foto", "No se permiten archivos diferentes a .jpeg o .jpg");
            return "redirect:/doctor/config";
        }
        String id = usuario.getId();
        String nombreArchivo= "fotosPerfil/perfil-" + id;
        try{
            uploadObject(file,nombreArchivo, "glowing-hearth-316315 ", "wenas");
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/doctor/config";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/doctor/config";
        }
    }
    @GetMapping("/logo")
    public ResponseEntity<byte[]> mostrarLogo(){
        int id=5;
        Optional<UxUi> opt = uxUiRepository.findById(id);
        if(opt.isPresent()){
            UxUi uxUi= opt.get();
            byte[] imagenComoBytes = uxUi.getLogo();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(uxUi.getLogoContentType()));
            return new ResponseEntity<>(imagenComoBytes, httpHeaders, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/color")
    public ResponseEntity<Map<String, String>> getColor() {
        UxUi uxUi = uxUiRepository.findById(5).orElse(null); // Obtener el último color guardado desde la base de datos

        Map<String, String> colorMap = new HashMap<>();
        assert uxUi != null;
        colorMap.put("color1", uxUi.getColorBar());
        colorMap.put("color2", uxUi.getColorBack());

        return ResponseEntity.ok(colorMap);
    }

}
