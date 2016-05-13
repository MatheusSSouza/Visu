import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickIntersection;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.Canvas3D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Visu extends JFrame implements MouseListener{
    Off polygon;
    PickCanvas screen;
    JFileChooser FilePick;
    //Mesmo quando nao chama setVisible, ele roda infinitamente
    //Problemmas com a luz(mama.off)
    
    public Visu(){
        //Configura o frame
        super("Visualizador");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(800, 400);
        
        //Configura o FileChooser
        FilePick = new JFileChooser();
        FilePick.setAcceptAllFileFilterUsed(false);
        FilePick.setFileFilter(new FileNameExtensionFilter("OFF", "off"));
        
        //Configura a tela
        Canvas3D cv = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        
        ViewingPlatform vp = new ViewingPlatform();
        vp.getViewPlatform().setActivationRadius(Float.MAX_VALUE);
        
        Viewer vw = new Viewer(cv);
        vw.getView().setPhysicalBody(new PhysicalBody());
        vw.getView().setPhysicalEnvironment(new PhysicalEnvironment());
        vw.getView().attachViewPlatform(vp.getViewPlatform());
        vw.getView().setBackClipDistance(500);
        vw.getView().setFrontClipDistance(0.01);
        vw.getView().setFieldOfView(Math.toRadians(90));
        
        
//        View v = new View();
//        v.setBackClipDistance(v.getBackClipDistance()*100);
//        v.setFrontClipDistance(v.getFrontClipDistance()/100);
//        v.addCanvas3D(cv);
//        System.out.print(cv.getView());
        
        //Declara a OFF (conteudo da tela)
        polygon = new Off(vp, vw);
        
        //Configura um PickCanvas
        screen = new PickCanvas(cv, polygon.getBranchGroup());
        //pc.setMode(PickTool.GEOMETRY_INTERSECT_INFO);
        //pc.setTolerance(10000f);
        //Permite interação entre o mouse e os objetos do Canvas3d
        
        //Configura os behavios(listeners)
        cv.addMouseListener(this);
        
        //Popula o frame
        this.add(cv, CENTER);
        this.add(menu(), NORTH);
        //this.add(mark(polygon), WEST);
        
        this.setVisible(true);
    }
    
    private JPanel menu(){//Constroi o painel da menu
        JPanel menu = new JPanel(new FlowLayout());
        menu.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/10));
        menu.setBackground(Color.blue);
        
        JButton btOpen = new JButton("Open File");
        JButton btPoint = new JButton("Point View");
        JButton btLine = new JButton("Line View");
        JButton btFill = new JButton("Fill View");
        
        //btOpen.setPreferredSize(new Dimension(50, 50));
        
        menu.add(btOpen);
        menu.add(btPoint);
        menu.add(btLine);
        menu.add(btFill);
        
        btOpen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                if(FilePick.showOpenDialog(rootPane) == JFileChooser.APPROVE_OPTION) {
                   polygon.OpenFile(FilePick.getSelectedFile());
                   System.out.println(FilePick.getSelectedFile());
                }
            }
        });
        btPoint.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                polygon.setPAttributes(polygon.POLYGON_POINT);
            }
        });
        btLine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                polygon.setPAttributes(polygon.POLYGON_LINE);
            }
        });
        btFill.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                polygon.setPAttributes(polygon.POLYGON_FILL);
            }
        });
        
        return menu;
    }
    
    private JPanel mark(Off root){
        JPanel mark = new JPanel();
        mark.setLayout(new BoxLayout(mark, BoxLayout.Y_AXIS));
        mark.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()));
        mark.setBackground(Color.red);
        
        mark.add(new JLabel(String.valueOf(root.getnPoints()) + " points"));
        mark.add(new JLabel("    "));
        mark.add(new JLabel(String.valueOf(root.getnFaces()) + " faces"));
        
        return mark;
    }
    
    public static void main(String[] args){
        new Visu();
    }

    public void mouseClicked(MouseEvent me) {
        screen.setShapeLocation(me);
        PickResult pr = screen.pickClosest();
        PickIntersection pi = pr.getClosestIntersection(screen.getStartPosition());
        System.out.println(pi.getPointCoordinates());
        polygon.selectPoint(pi.getPointCoordinates());
    }

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
}
