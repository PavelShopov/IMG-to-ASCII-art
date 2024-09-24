


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class IMGtoAscii {
    static private BufferedImage img;
    static private Scanner scan;
    static private int width;
    static private int height;
    static private int[][] pixels;
    static String name;
    static String type;
    public static void main(String[] args) {
        try{
            scan=new Scanner(System.in);
            System.out.println("Please enter the image you want to be changed into ASCII art!");
            name=scan.nextLine();   
            type=scan.nextLine();   
            img=ImageIO.read(new File("Images\\"+name+"."+type));
            width=img.getWidth();
            height=img.getHeight();

            scaleGrayscale();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Problem");
        }
            try(PrintWriter writer = new PrintWriter("ASCII\\"+name+".html")){
            int pixelSize=8;
            int calcSize=(width*6)+(height*6);
            if(calcSize>=2000 && calcSize<3500 ){
                pixelSize=4;
            }
            else if(calcSize>=3500 ){
                pixelSize=2;
                
            }
            writer.print("<!DOCTYPE html> \n <html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n<title>Document</title>\n<style>\nbody{\n background-color: rgb(39, 38, 38);color:azure;font-size: "+pixelSize+"px;font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;text-justify: none;   text-align: center;margin: 0%;padding: 0%;}\n</style></head>\n<body>\n<pre>");
            
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char ASCII= ASCII_VAl(pixels[y][x]);
                        writer.print(ASCII);
                        writer.print(ASCII);
                    }
                    writer.println();
                }
                writer.print("\r\n" + //
                                        "    </pre>\r\n" + //
                                        "</body>\r\n" + //
                                        "</html>");
            } catch (IOException e) {
                e.printStackTrace();
            }                    
            
    }
    private static void scaleGrayscale(){
        height/=6;
        width/=6;
        BufferedImage rescImage = new BufferedImage(width, height,img.getType());
        Graphics2D g2d = rescImage.createGraphics();
        pixels=new int[height][width];
        g2d.drawImage(img, 0, 0,width,height, null);
        img=rescImage;
        //Optional contrast and brightness adjust
        // float brightenFactor = 1.2f;
        // RescaleOp op = new RescaleOp(brightenFactor, 0, null);
        // img = op.filter(img, img);
        int[] luminescenceLevels = new int[] { 0, 28, 56, 84, 112, 140, 168, 196, 224, 255 };
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = img.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int gray = (red + green + blue) / 3;
                int closestLuminescence = luminescenceLevels[0];
                for (int level : luminescenceLevels) {
                    if (Math.abs(gray - level) < Math.abs(gray - closestLuminescence)) {
                        closestLuminescence = level;
                    }
                }
                int newPixel = (closestLuminescence << 16) | (closestLuminescence << 8) | closestLuminescence;
                pixels[y][x] = newPixel;
                img.setRGB(x, y, newPixel);    
            }
        }
    }
    private static char ASCII_VAl(int pixel){
                char ASCII=' ';
                switch (pixel) {
                    case 0: ASCII=' ';break;
                    case 1842204: ASCII='.';break;
                    case 3684408: ASCII='^'; break;
                    case 5526612:  ASCII='*';break;
                    case 7368816:  ASCII=':';break;
                    case 9211020:  ASCII='!'; break;
                    case 11053224: ASCII='>'; break;
                    case 12895428:  ASCII='P'; break;
                    case 14737632:  ASCII='@';break;
                     case 16777215:  ASCII='█';break;
                }
                // switch (pixel) {
                //     case 16777215:ASCII=' ';break;
                //     case 14737632: ASCII='.';break;
                //     case 12895428: ASCII='^'; break;
                //     case 11053224:  ASCII='*';break;
                //     case 9211020:  ASCII='/';break;
                //     case 7368816:  ASCII='!'; break;
                //     case 5526612: ASCII='>'; break;
                //     case 3684408:  ASCII='P'; break;
                //     case 1842204:  ASCII='@';break;
                //      case 0: ASCII='█';break;
                // }
              
                
               return ASCII;
    }

}
