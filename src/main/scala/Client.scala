import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

//typedef struct _client_t
//{
//  int s;
//  AbstractFile *ro_file;
//  AbstractFile *wo_file;
//  DIR *dir;
//  char *dirpath;
//  uint8_t *buf;
//  int connected;
//  struct in_addr ip_addr;
//  thread_t thread;
//  uint16_t CD_SECTOR_SIZE;
//} client_t;

//Handles each Client Connection
class Client extends Runnable {
  var s = 0
  var ro_file = null
  var wo_file = null
  var dir = null
  var dirpath = null
  val connected = new AtomicBoolean(true)
  val CD_SECTOR_SIZE = 2352


  override def run(): Unit = {
    val cmd = null
    var ret = 0;
    
  }
}
