import java.io.File
import java.net.InetAddress

object PS3Netserv {
val DEFAULT_PORT: Int = 38008
val MAX_CLIENTS: Int = 5

val USAGE = s"""Usage: %s [--rootDirectory] [--port] [--whitelist]
  Default port: $DEFAULT_PORT
  Whitelist: x.x.x.x, where x is 0-255 or *
  (e.g 192.168.1.* to allow only connections from 192.168.1.0-192.168.1.255) """

  def main(args: Array[String]) {
    if (args.length == 0) println(USAGE)
    else {
      val arglist = args.toList
      type OptionMap = Map[Symbol, String]

      @scala.annotation.tailrec
      def nextOption(map : OptionMap, list: List[String]): OptionMap = {

        list match {
          case Nil => map
          case "--rootDirectory" :: value :: tail =>
            nextOption(map ++ Map(Symbol("rootDirectory") -> value), tail)
          case "--port" :: value :: tail =>
            nextOption(map ++ Map(Symbol("port") -> value), tail)
          case "--whitelist" :: value :: tail =>
            nextOption(map ++ Map(Symbol("whitelist") -> value), tail)
          case option :: tail => println("Unknown option "+option)
            System.exit(1)
            map
        }
      }

      val options = nextOption(Map(),arglist)
      //for now lets output the args for debugging
      println(options)
      val port = options.get(Symbol("port")).flatMap(_.toIntOption)

      port match {
        case Some(v) => if(v < 1 || v > 65535) {
          println(s"Port#: $v is invalid.  Port must be in 1-65535 range. ")
          System.exit(1)
        }
        case None =>
          println("Port must be in 1-65535 range. ")
          System.exit(1)
      }

      val rootDirectory = options.getOrElse(Symbol("rootDirectory"), "./").replaceFirst("^~", System.getProperty("user.home"))
      val folder = new File(rootDirectory)
      if(!folder.canRead){
        println(s"Can't read ${folder.getAbsolutePath}")
        System.exit(1)
      }
      val directories = folder.listFiles.filter(_.isDirectory).map(file => file.getName -> file).toMap

      val whiteList = options.getOrElse(Symbol("whitelist"), "*.*.*.*").split(".")
      if(whiteList.length < 4) {
        println("Wrong whitelist format")
        System.exit(1)
      }

      var wl_start = 0L
      var wl_end = 0L
      try {
        var i = 0
        while (i < whiteList.length) {
          if (whiteList(i) == "*")
            wl_end = wl_end | (0xFF << (i * 8))
          else {
            wl_start |= (whiteList(i).toInt << (i * 8))
            wl_end |= (whiteList(i).toInt << (i * 8))
          }
          i+=1
        }
      } catch {
        case NumberFormatException => println("invalid whitelist param")
      }

      println(s"Whitelist: ${String.format("%08x", wl_start)} - ${String.format("%08x", wl_end)}")

      val ip = InetAddress.getLocalHost
      val hostname = ip.getHostName
      println(s"Current Host IP: $ip:$port")
      println(s"Current Hostname: $hostname")

      val server = new Server()

      while(true){
        println("Waiting for a Client...")


      }
    }
  }
}
