import java.io.{BufferedReader, PrintWriter, Reader, Writer}
import java.net.Socket

class Server {
  private var clientSocket: Socket = null
  private var out: Writer = null
  private var in: Reader = null

  def startConnection(ip: String, port: Int): Unit = {
    clientSocket = new Socket(ip, port)
    out = new PrintWriter(clientSocket.getOutputStream, true)
    in = new BufferedReader(new Nothing(clientSocket.getInputStream))
  }

  def stopConnection(): Unit = {
    in.close()
    out.close()
    clientSocket.close()
  }
}