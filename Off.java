package Visu;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.PointLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.GeometryArray;
import javax.vecmath.Point3d;
import javax.vecmath.Color3f;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import javafx.geometry.Point3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.vecmath.Vector3d;

import Visu.mateface.*;

public class Off extends SimpleUniverse {

    public final int POLYGON_POINT = javax.media.j3d.PolygonAttributes.POLYGON_POINT;
    public final int POLYGON_LINE = javax.media.j3d.PolygonAttributes.POLYGON_LINE;
    public final int POLYGON_FILL = javax.media.j3d.PolygonAttributes.POLYGON_FILL;
    public final int GROUP_BG = 0;
    public final int LIGHT_BG = 1;
    public final int SHAPE_TG = 0;
    public final int MWZ_TG = 1;
    public final int MR_TG = 2;
    public final int MT_TG = 3;
    public final int SELECT_TG = 4;
//    private Color3f color = new Color3f(Color.GREEN);//Manter a cor atual

    private int nvertex;
    private double maxdist;
    private Shape3D shape, select, bordo;
    private BranchGroup bg;
    private ArrayList<Point3d> vertex;
    private ArrayList<Celula> mf;
    
    private mfMalha m;

    MouseWheelZoom mwz;
    MouseRotate mr;
    MouseTranslate mt;

    public Off(Canvas3D cv) {
        super(cv);

        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        bg.insertChild(tg, GROUP_BG);

        mwz = new MouseWheelZoom();
        mr = new MouseRotate();
        mt = new MouseTranslate();
        mwz.setFactor(mwz.getFactor() * -1);
        mwz.setTransformGroup(tg);
        mr.setTransformGroup(tg);
        mt.setTransformGroup(tg);
        mwz.setSchedulingBounds(tg.getBounds());
        mr.setSchedulingBounds(tg.getBounds());
        mt.setSchedulingBounds(tg.getBounds());

        tg.insertChild(mwz, MWZ_TG);
        tg.insertChild(mr, MR_TG);
        tg.insertChild(mt, MT_TG);
        
        bg.insertChild(new BranchGroup(), LIGHT_BG);
    }

    public Off(ViewingPlatform vp, Viewer vw) {
        super(vp, vw);

        bg = new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        bg.insertChild(tg, GROUP_BG);

        mwz = new MouseWheelZoom();
        mr = new MouseRotate();
        mt = new MouseTranslate();
        mwz.setFactor(mwz.getFactor() * -1);
        mwz.setTransformGroup(tg);
        mr.setTransformGroup(tg);
        mt.setTransformGroup(tg);
        mwz.setSchedulingBounds(tg.getBounds());
        mr.setSchedulingBounds(tg.getBounds());
        mt.setSchedulingBounds(tg.getBounds());

        Text2D marks = new Text2D("hbqvirvndqmpvruvhn0idn", new Color3f(Color.YELLOW), "Helvetica", 12, Font.PLAIN);
        marks.setString("hjb9ygvu");
        bg.addChild(marks);
        //bg.add(marks);
        //
        addlight();
        //bg.insertChild(new BranchGroup(), LIGHT_BG);
//        System.out.println(bg.getBounds());
        this.addBranchGraph(bg);
//        System.out.println(marks);
    }

    public void OpenFile(File file) {
        bg.detach();
        TransformGroup tg = (TransformGroup) bg.getChild(GROUP_BG);
        tg.removeAllChildren();

        shape = loadoff(file);
        tg.insertChild(shape, SHAPE_TG);

        mwz.setTransformGroup(tg);
        tg.insertChild(mwz, MWZ_TG);
        mwz.setSchedulingBounds(tg.getBounds());
        mr.setTransformGroup(tg);
        tg.insertChild(mr, MR_TG);
        mr.setSchedulingBounds(tg.getBounds());
        mt.setTransformGroup(tg);
        tg.insertChild(mt, MT_TG);
        mt.setSchedulingBounds(tg.getBounds());
        

//        TransformGroup tt = new TransformGroup();
//        Text2D marks = new Text2D("hey", new Color3f(Color.YELLOW),
//                "Helvetica", 80, Font.PLAIN);
//        tg.addChild(marks);
//        Transform3D t = new Transform3D();
//        t.setTranslation(null);
///        tt.setTransform(null);

        addlight();
        this.addBranchGraph(bg);
        this.getViewingPlatform().setNominalViewingTransform();
//        Transform3D tf = new Transform3D();
//        tf.setTranslation(new Vector3d(0, 0, -maxdist*(Math.sqrt(maxdist+1))));
//        tg.setTransform(tf);
        Transform3D tf = new Transform3D();
        maxdist = Double.MIN_VALUE;
//        for (int i = 0; i < m.vertice.size(); i++) {
//            if(m.vertice.get(i).getCoords(0)>maxdist){
//                maxdist = m.vertice.get(i).getCoords(0);
//            }
//            if(m.vertice.get(i).getCoords(1)>maxdist){
//                maxdist = m.vertice.get(i).getCoords(1);
//            }
//            if(m.vertice.get(i).getCoords(2)>maxdist){
//                maxdist = m.vertice.get(i).getCoords(2);
//            }
//        }
//        tf.setScale(1 / maxdist);
//        tg.setTransform(tf);
        tf = new Transform3D();
        tf.setTranslation(new Vector3d(0, 0, 0));
        tg.setTransform(tf);


        this.getViewer().getView().setBackClipDistance(500);
        this.getViewer().getView().setFrontClipDistance(0.01);
        this.getViewer().getView().setFieldOfView(Math.toRadians(90));
        mfPlay.play2();
        //System.out.println(maxdist);
    }

    private Shape3D loadoff(File file) {
        m = new mfMalha();
        //TriangleArray ta = new TriangleArray(9, TriangleArray.COORDINATES | GeometryArray.COLOR_3); 
        
//        mfSoLemat l = new mfSoLemat();
//        TriangleArray ta = l.leituraOFF(m, file.getAbsolutePath());
//        Point3d p = new Point3d();
//        System.out.println(ta.getVertexCount());
//        for (int j = 0; j < ta.getVertexCount(); j++) {
// 
//            ta.getCoordinate(j, p);
//            System.out.println(p);
//        }
        
//        Color3f[] cl = new Color3f[m.t];
//        for (int i = 0; i < cl.length; i++) {
//            cl[i] = new Color3f(Color.blue);
//        }
//        ta.setColors(0, cl);
//        
        TriangleArray ta = mfPlay.play1(file.getAbsolutePath());
        
        return makeShape(ta);
//        maxdist = 0;
//        try {
//            Scanner sc = new Scanner(file).useLocale(java.util.Locale.US);
//            sc.nextLine();
//
//            int nv = sc.nextInt();//Numero de vertices
//            int nf = sc.nextInt();//Numero de faces
//            int na = sc.nextInt();//Numero de arestas
//            nvertex = nv;
//
//            vertex = new ArrayList<>();
//            TriangleArray ta = new TriangleArray(nf * 3, TriangleArray.COORDINATES);// | GeometryArray.COLOR_3);
//            mf = new ArrayList<>();
//            Color3f[] cl = new Color3f[nf * 3];
//
//            //carrega os vertices da .off no array vertex
//            for (int x = 0; x < nv; x++) {
//                vertex.add(new Point3d(sc.nextFloat(), sc.nextFloat(), sc.nextFloat()));
//                if (Math.abs(vertex.get(x).x) > maxdist) {
//                    maxdist = Math.abs(vertex.get(x).x);
//                }
//                if (Math.abs(vertex.get(x).y) > maxdist) {
//                    maxdist = Math.abs(vertex.get(x).y);
//                }
//                if (Math.abs(vertex.get(x).z) > maxdist) {
//                    maxdist = Math.abs(vertex.get(x).z);
//                }
//
//            }
//
//            maxdist *= 2;
//
//            //Normaliza o vertex
//            for (int x = 0; x < nv; x++) {
//                Point3d p = vertex.remove(x);
//                p.setX(p.getX() / maxdist);
//                p.setY(p.getY() / maxdist);
//                p.setZ(p.getZ() / maxdist);
//                vertex.add(x, p);
//            }
//            //Printer.print(points, "C:\\Users\\matheus\\Desktop\\cowar.txt");
//            for (int x = 0; x < nf * 3;) {
//                sc.nextInt();
//                int i1 = sc.nextInt();
//                int i2 = sc.nextInt();
//                int i3 = sc.nextInt();
//                Point3d p1 = vertex.get(i1);
//                Point3d p2 = vertex.get(i2);
//                Point3d p3 = vertex.get(i3);
//                
//                /**
//                 * Colorir *
//                 */
////                if (p1.x > 0) {
////                    cl[x] = new Color3f(Color.CYAN);
////                } else {
////                    cl[x] = new Color3f(Color.ORANGE);
////                }
////                if (p2.x > 0) {
////                    cl[x] = new Color3f(Color.CYAN);
////                } else {
////                    cl[x] = new Color3f(Color.ORANGE);
////                }
////                if (p3.x > 0) {
////                    cl[x] = new Color3f(Color.CYAN);
////                } else {
////                    cl[x] = new Color3f(Color.ORANGE);
////                }
//
//                mf.add(new Celula(i1, i2, i3));
//                ta.setCoordinate(x++, p1);
//                ta.setCoordinate(x++, p2);
//                ta.setCoordinate(x++, p3);
//            }
//            //ta.setColors(0, cl);//Colorir
//            //Printer.print(ta, "C:\\Users\\matheus\\Desktop\\cowta.txt");
//            
//            //calcOposto();
//            
//            //print();
//            return makeShape(ta);
//        } catch (Exception e) {
//            System.out.println("Erro ao tentar contruir OFF\n" + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
    }

    private Shape3D makeShape(TriangleArray tarray) {
        GeometryInfo forminfo = new GeometryInfo(tarray);
        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(forminfo);

        Appearance a = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.blue));
        PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        pa.setBackFaceNormalFlip(true);
        a.setMaterial(mat);
        a.setPolygonAttributes(pa);
        

        return new Shape3D(forminfo.getGeometryArray(), a);
    }

    public void setCollor(Color cl) {
        bg.detach();
        Appearance a = shape.getAppearance();
        Material mat = a.getMaterial();
        mat.setDiffuseColor(new Color3f(cl));
        a.setMaterial(mat);

        shape.setAppearance(a);
        this.addBranchGraph(bg);
    }

    public void setPAttributes(int i) {
        bg.detach();
        Appearance a = shape.getAppearance();
        PolygonAttributes pa = a.getPolygonAttributes();
        pa.setPolygonMode(i);
        a.setPolygonAttributes(pa);

        shape.setAppearance(a);
        this.addBranchGraph(bg);
    }

    public void selectPoint2(Point3d ps){
        PointArray pa = new PointArray(1, GeometryArray.COORDINATES);
        pa.setCoordinate(0, new Point3d());
        double min = Double.MAX_VALUE;
        Point3d paux;
        int s = 0;
        for (int i = 0; i < m.vertice.size(); i++) {
            m.vertice.get(i).getCoords(0);
            paux = new Point3d(m.vertice.get(i).getCoords(0), m.vertice.get(i).getCoords(1), m.vertice.get(i).getCoords(2));
            if (dist(paux, ps) < min) {
                min = dist(paux, ps);
                s = i;
            }
            if(m.vertice.get(i).getCoords(0) == ps.getX()
                && m.vertice.get(i).getCoords(1) == ps.getY()
                && m.vertice.get(i).getCoords(2) == ps.getZ()){
            }
            
        }
        Appearance a = new Appearance();
        PointAttributes p = new PointAttributes();
        p.setPointSize(3);
        a.setPointAttributes(p);
        pa.setCoordinate(0, new Point3d(m.vertice.get(s).getCoords(0), m.vertice.get(s).getCoords(1), m.vertice.get(s).getCoords(2)));
        select = new Shape3D(pa, a);
        TransformGroup tg = (TransformGroup) bg.getChild(GROUP_BG);
        bg.detach();
        
        tg.removeChild(SELECT_TG);
        tg.insertChild(select, SELECT_TG);
        
        this.addBranchGraph(bg);
    }
    
    
    public void selectPoint(Point3d ps) {
        TriangleArray ta = (TriangleArray) shape.getGeometry();
        TransformGroup tg = (TransformGroup) bg.getChild(GROUP_BG);
        //tg.removeChild(SHAPE_TG);
        Point3d pi = new Point3d();
        Color3f[] cl = new Color3f[ta.getVertexCount()];
        double min = Double.MAX_VALUE;
        int s = 0;
        double dist;
        for (int i = 0; i < ta.getVertexCount(); i++) {
            ta.getCoordinate(i, pi);
            dist = dist(pi, ps);
            if (dist < min) {
                min = dist;
                s = i;
            }
//            if(pi == ps){
//                //System.out.println(pi +" == " + ps);
//                cl[i] = new Color3f(Color.CYAN);
//            }else{
//                //System.out.println(pi +" != " + ps);
//                cl[i] = new Color3f(Color.DARK_GRAY);
//            }
        }
        PolygonAttributes pa = shape.getAppearance().getPolygonAttributes();
        bg.detach();
        
        ta.setCapability(TriangleArray.COORDINATES | GeometryArray.COLOR_3);
        ta.setCapability(GeometryArray.COLOR_3);
        ta.setColor(s, new Color3f(Color.CYAN));
        shape = makeShape(ta);
        shape.getAppearance().setPolygonAttributes(pa);
        
        tg.removeChild(shape);
        tg.insertChild(shape, SHAPE_TG);
        this.addBranchGraph(bg);
    }

    private double dist(Point3d p1, Point3d p2) {
        return Math.sqrt(
                Math.pow(p1.x - p2.x, 2)
                + Math.pow(p1.y - p2.y, 2)
                + Math.pow(p1.z - p2.z, 2)
        );
    }
    
    private void calcOposto(){
        for (int i = 0; i < mf.size(); i++) {
            int[] v = mf.get(i).getCoordinates();
            int[] o = {-1, -1, -1};
            for (int j = 0; j < v.length; j++) {
                for (int k = 0; k < mf.size(); k++) {
                    if(mf.get(k).contains(v[(j+1)%3], v[(j+2)%3])){
                        o[j] = k;
                    }
                }
            }
            mf.get(i).setOposto(o);
        }
    }
    
    public void showBordo(){
//        for (int i = 0; i < ; i++) {
//            
//        }
    }

    private void addlight() {
        bg.removeChild(LIGHT_BG);
        BranchGroup light = new BranchGroup();
        light.setBounds(new BoundingSphere(new Point3d(0, 0, 0), maxdist * 3));
        AmbientLight la = new AmbientLight(new Color3f(0.1f, 0.1f, 0.1f));
        la.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), maxdist * 3));
        light.addChild(la);

        PointLight pl = new PointLight();
        pl.setColor(new Color3f(Color.WHITE));
        pl.setPosition(0, 0, 2);
        pl.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), maxdist * 3));
        light.addChild(pl);
        bg.insertChild(light, LIGHT_BG);
    }

    public int getnPoints() {
        return nvertex;
    }

    public int getnFaces() {
        TriangleArray ta = (TriangleArray) shape.getGeometry();
        return (ta.getVertexCount() / 3);
    }

    public TransformGroup getTransformGroup() {
        return (TransformGroup) bg.getChild(GROUP_BG);
    }

    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void print() {
        for (Celula c : mf) {
            System.out.println(c);
        }
        System.out.println(mf.size());
    }

    //ViewingPlatform vp = this.getViewingPlatform().getViewPlatform().set
    //this.getViewingPlatform().setCapability(ViewingPlatform.ALLOW_AUTO_COMPUTE_BOUNDS_WRITE);
    //this.getViewingPlatform().setBoundsAutoCompute(true);
    //this.getViewingPlatform().compile();
    
    class Celula{
        int[] vertex;//id do ponto armazenado em points
        int[] opostos;//id das faces armazenadas em mf
        
        public Celula(int x, int y, int z){
            vertex = new int[3];
            vertex[0] = x;
            vertex[1] = y;
            vertex[2] = z;
            opostos = new int[3];
        }

        public int[] getCoordinates() {
            return vertex;
        }
        
        public boolean contains(int x, int y){
            if(x == y){
                System.err.println("Erro: Contains invalido.");
                return false;
            }
            else{
                boolean bx = false, by = false;
                for (int i : vertex) {
                    if(i == x) bx = true;
                    if(i == y) by = true;
                }
                return bx && by;
            }
        }
        
        /**
         * 
         * @param idPonto - O id do ponto dessa face que tem como face oposta a face de idFadce
         * @param idFace - O id da face que e oposta ao idPonto dessa face 
         */
        
        public void setOposto(int[] ops){
            opostos = Arrays.copyOf(ops, opostos.length);
        }
        
        public String toString(){
            return Arrays.toString(vertex)+ "    \t" + Arrays.toString(opostos);
        }
    }
}
