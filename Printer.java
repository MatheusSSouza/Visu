
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

public class Printer {
    public static void print(TriangleArray ta, String file) throws IOException{
        try {
            RandomAccessFile fr = new RandomAccessFile(file, "rw");
            Point3f p = new Point3f();
            for (int i = 0; i < ta.getVertexCount(); i++) {
                ta.getCoordinate(i, p);
                fr.writeChars(p.toString());
                fr.write('\r');
                fr.write('\n');
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void print(ArrayList ta, String file) throws IOException{
        try {
            RandomAccessFile fr = new RandomAccessFile(file, "rw");
            Point3d p = new Point3d();
            for (int i = 0; i < ta.size(); i++) {
                p = (Point3d)ta.get(i);
                fr.writeChars(p.toString());
                fr.write('\r');
                fr.write('\n');
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
