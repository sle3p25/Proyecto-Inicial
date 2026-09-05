import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * Any CSS3 standard color name (e.g. "gold", "dimgray", "orchid") is
     * accepted, matching the project's requirement that symbol color
     * names follow the CSS standard. Unknown names fall back to black.
     * @param  newColour   the new colour for the foreground of the Canvas
     */
    public void setForegroundColor(String colorString){
        graphic.setColor(resolveCssColor(colorString));
    }

    /**
     * Looks up a CSS3 standard color name (case-insensitive) and returns
     * the matching java.awt.Color, or black if the name is not
     * recognized.
     */
    private static Color resolveCssColor(String colorString) {
        if (colorString == null) {
            return Color.black;
        }
        Color color = CSS_COLORS.get(colorString.trim().toLowerCase());
        return (color != null) ? color : Color.black;
    }

    /** CSS3 extended color keyword table (name -> RGB). */
    private static final HashMap<String, Color> CSS_COLORS = buildCssColorTable();

    private static HashMap<String, Color> buildCssColorTable() {
        HashMap<String, Color> t = new HashMap<String, Color>();
        t.put("aliceblue", new Color(0xF0F8FF));
        t.put("antiquewhite", new Color(0xFAEBD7));
        t.put("aqua", new Color(0x00FFFF));
        t.put("aquamarine", new Color(0x7FFFD4));
        t.put("azure", new Color(0xF0FFFF));
        t.put("beige", new Color(0xF5F5DC));
        t.put("bisque", new Color(0xFFE4C4));
        t.put("black", new Color(0x000000));
        t.put("blanchedalmond", new Color(0xFFEBCD));
        t.put("blue", new Color(0x0000FF));
        t.put("blueviolet", new Color(0x8A2BE2));
        t.put("brown", new Color(0xA52A2A));
        t.put("burlywood", new Color(0xDEB887));
        t.put("cadetblue", new Color(0x5F9EA0));
        t.put("chartreuse", new Color(0x7FFF00));
        t.put("chocolate", new Color(0xD2691E));
        t.put("coral", new Color(0xFF7F50));
        t.put("cornflowerblue", new Color(0x6495ED));
        t.put("cornsilk", new Color(0xFFF8DC));
        t.put("crimson", new Color(0xDC143C));
        t.put("cyan", new Color(0x00FFFF));
        t.put("darkblue", new Color(0x00008B));
        t.put("darkcyan", new Color(0x008B8B));
        t.put("darkgoldenrod", new Color(0xB8860B));
        t.put("darkgray", new Color(0xA9A9A9));
        t.put("darkgreen", new Color(0x006400));
        t.put("darkgrey", new Color(0xA9A9A9));
        t.put("darkkhaki", new Color(0xBDB76B));
        t.put("darkmagenta", new Color(0x8B008B));
        t.put("darkolivegreen", new Color(0x556B2F));
        t.put("darkorange", new Color(0xFF8C00));
        t.put("darkorchid", new Color(0x9932CC));
        t.put("darkred", new Color(0x8B0000));
        t.put("darksalmon", new Color(0xE9967A));
        t.put("darkseagreen", new Color(0x8FBC8F));
        t.put("darkslateblue", new Color(0x483D8B));
        t.put("darkslategray", new Color(0x2F4F4F));
        t.put("darkslategrey", new Color(0x2F4F4F));
        t.put("darkturquoise", new Color(0x00CED1));
        t.put("darkviolet", new Color(0x9400D3));
        t.put("deeppink", new Color(0xFF1493));
        t.put("deepskyblue", new Color(0x00BFFF));
        t.put("dimgray", new Color(0x696969));
        t.put("dimgrey", new Color(0x696969));
        t.put("dodgerblue", new Color(0x1E90FF));
        t.put("firebrick", new Color(0xB22222));
        t.put("floralwhite", new Color(0xFFFAF0));
        t.put("forestgreen", new Color(0x228B22));
        t.put("fuchsia", new Color(0xFF00FF));
        t.put("gainsboro", new Color(0xDCDCDC));
        t.put("ghostwhite", new Color(0xF8F8FF));
        t.put("gold", new Color(0xFFD700));
        t.put("goldenrod", new Color(0xDAA520));
        t.put("gray", new Color(0x808080));
        t.put("grey", new Color(0x808080));
        t.put("green", new Color(0x008000));
        t.put("greenyellow", new Color(0xADFF2F));
        t.put("honeydew", new Color(0xF0FFF0));
        t.put("hotpink", new Color(0xFF69B4));
        t.put("indianred", new Color(0xCD5C5C));
        t.put("indigo", new Color(0x4B0082));
        t.put("ivory", new Color(0xFFFFF0));
        t.put("khaki", new Color(0xF0E68C));
        t.put("lavender", new Color(0xE6E6FA));
        t.put("lavenderblush", new Color(0xFFF0F5));
        t.put("lawngreen", new Color(0x7CFC00));
        t.put("lemonchiffon", new Color(0xFFFACD));
        t.put("lightblue", new Color(0xADD8E6));
        t.put("lightcoral", new Color(0xF08080));
        t.put("lightcyan", new Color(0xE0FFFF));
        t.put("lightgoldenrodyellow", new Color(0xFAFAD2));
        t.put("lightgray", new Color(0xD3D3D3));
        t.put("lightgreen", new Color(0x90EE90));
        t.put("lightgrey", new Color(0xD3D3D3));
        t.put("lightpink", new Color(0xFFB6C1));
        t.put("lightsalmon", new Color(0xFFA07A));
        t.put("lightseagreen", new Color(0x20B2AA));
        t.put("lightskyblue", new Color(0x87CEFA));
        t.put("lightslategray", new Color(0x778899));
        t.put("lightslategrey", new Color(0x778899));
        t.put("lightsteelblue", new Color(0xB0C4DE));
        t.put("lightyellow", new Color(0xFFFFE0));
        t.put("lime", new Color(0x00FF00));
        t.put("limegreen", new Color(0x32CD32));
        t.put("linen", new Color(0xFAF0E6));
        t.put("magenta", new Color(0xFF00FF));
        t.put("maroon", new Color(0x800000));
        t.put("mediumaquamarine", new Color(0x66CDAA));
        t.put("mediumblue", new Color(0x0000CD));
        t.put("mediumorchid", new Color(0xBA55D3));
        t.put("mediumpurple", new Color(0x9370DB));
        t.put("mediumseagreen", new Color(0x3CB371));
        t.put("mediumslateblue", new Color(0x7B68EE));
        t.put("mediumspringgreen", new Color(0x00FA9A));
        t.put("mediumturquoise", new Color(0x48D1CC));
        t.put("mediumvioletred", new Color(0xC71585));
        t.put("midnightblue", new Color(0x191970));
        t.put("mintcream", new Color(0xF5FFFA));
        t.put("mistyrose", new Color(0xFFE4E1));
        t.put("moccasin", new Color(0xFFE4B5));
        t.put("navajowhite", new Color(0xFFDEAD));
        t.put("navy", new Color(0x000080));
        t.put("oldlace", new Color(0xFDF5E6));
        t.put("olive", new Color(0x808000));
        t.put("olivedrab", new Color(0x6B8E23));
        t.put("orange", new Color(0xFFA500));
        t.put("orangered", new Color(0xFF4500));
        t.put("orchid", new Color(0xDA70D6));
        t.put("palegoldenrod", new Color(0xEEE8AA));
        t.put("palegreen", new Color(0x98FB98));
        t.put("paleturquoise", new Color(0xAFEEEE));
        t.put("palevioletred", new Color(0xDB7093));
        t.put("papayawhip", new Color(0xFFEFD5));
        t.put("peachpuff", new Color(0xFFDAB9));
        t.put("peru", new Color(0xCD853F));
        t.put("pink", new Color(0xFFC0CB));
        t.put("plum", new Color(0xDDA0DD));
        t.put("powderblue", new Color(0xB0E0E6));
        t.put("purple", new Color(0x800080));
        t.put("rebeccapurple", new Color(0x663399));
        t.put("red", new Color(0xFF0000));
        t.put("rosybrown", new Color(0xBC8F8F));
        t.put("royalblue", new Color(0x4169E1));
        t.put("saddlebrown", new Color(0x8B4513));
        t.put("salmon", new Color(0xFA8072));
        t.put("sandybrown", new Color(0xF4A460));
        t.put("seagreen", new Color(0x2E8B57));
        t.put("seashell", new Color(0xFFF5EE));
        t.put("sienna", new Color(0xA0522D));
        t.put("silver", new Color(0xC0C0C0));
        t.put("skyblue", new Color(0x87CEEB));
        t.put("slateblue", new Color(0x6A5ACD));
        t.put("slategray", new Color(0x708090));
        t.put("slategrey", new Color(0x708090));
        t.put("snow", new Color(0xFFFAFA));
        t.put("springgreen", new Color(0x00FF7F));
        t.put("steelblue", new Color(0x4682B4));
        t.put("tan", new Color(0xD2B48C));
        t.put("teal", new Color(0x008080));
        t.put("thistle", new Color(0xD8BFD8));
        t.put("tomato", new Color(0xFF6347));
        t.put("turquoise", new Color(0x40E0D0));
        t.put("violet", new Color(0xEE82EE));
        t.put("wheat", new Color(0xF5DEB3));
        t.put("white", new Color(0xFFFFFF));
        t.put("whitesmoke", new Color(0xF5F5F5));
        t.put("yellow", new Color(0xFFFF00));
        t.put("yellowgreen", new Color(0x9ACD32));
        return t;
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }

}

