function getCurrentURL () {
  return window.location.href
}
// Example
const url = getCurrentURL()
const QRcontenedor = document.getElementById('contenedorQR')
new QRCode(QRcontenedor, url)
