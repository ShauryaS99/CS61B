import java.util.HashMap;
import java.util.Map;
import static java.lang.Math.abs;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
        //measurement into filenames
        //from lat and long find zoom
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //sets all params
        double raster_ul_lon = params.get("ullon");
        double raster_ul_lat = params.get("ullat");
        double raster_lr_lon = params.get("lrlon");
        double raster_lr_lat = params.get("lrlat");
        //checks if query_success
        boolean query_success = true;
        if ((raster_lr_lon < MapServer.ROOT_ULLON) || (raster_ul_lon > MapServer.ROOT_LRLON
                || (raster_ul_lat < MapServer.ROOT_LRLAT)
                || (raster_lr_lat > MapServer.ROOT_ULLAT))) {
            query_success = false;
        }
        double raster_width = abs(raster_ul_lon - raster_lr_lon);
        double raster_height = abs(raster_ul_lat - raster_lr_lat);
        //zoomlevel
        double londpp = (raster_width) * 288200 / params.get("w");
        int depth;
        if (londpp > 98.94561767578125) {
            depth = 0;
        } else if (londpp > 49.472808837890625) {
            depth = 1;
        } else if (londpp > 24.736404418945312) {
            depth = 2;
        } else if (londpp > 12.368202209472656) {
            depth = 3;
        } else if (londpp > 6.184101104736328) {
            depth = 4;
        } else if (londpp > 3.092050552368164) {
            depth = 5;
        } else if (londpp > 1.546025276184082) {
            depth = 6;
        } else {
            depth = 7;
        }
        //images to use
        double tilesizelon = abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / Math.pow(2, depth);
        double tilesizelat = abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);

        //iterates through long # of nearest box in top left (Ai)
        int near_ul_lon = (int) abs((MapServer.ROOT_ULLON - raster_ul_lon) / tilesizelon);
        // long value of top left (A)
        double img_tl_long = MapServer.ROOT_ULLON + (near_ul_lon * tilesizelon);
        int top_right_long = (int) (Math.pow(2, depth) - abs((MapServer.ROOT_LRLON - raster_lr_lon)
                / tilesizelon)) + 1;
        int numxtiles = abs(top_right_long - near_ul_lon);

        //iterates through lat # of nearest box in top left (Bi)
        int near_ul_lat = (int) abs((MapServer.ROOT_ULLAT - raster_ul_lat) / tilesizelat);
        // lat value of top left (B)
        double img_tl_lat = MapServer.ROOT_ULLAT - (near_ul_lat * tilesizelat);
        int bottom_left_lat = (int) (Math.pow(2, depth) - abs((MapServer.ROOT_LRLAT - raster_lr_lat)
                / tilesizelat)) + 1;
        int numytiles = abs(bottom_left_lat - near_ul_lat);
        String[][] render_grid = new String[numytiles][numxtiles];

        for (int a = 0; a < numytiles; a++) { // a is iterating through vertically
            for (int b = 0; b < numxtiles; b++) { // b is iterating through horizontally
                String imgname = getimg(depth, near_ul_lon + b, near_ul_lat + a);
                render_grid[a][b] = imgname;
            }
        }
        raster_ul_lon = img_tl_long;
        raster_ul_lat = img_tl_lat;
        raster_lr_lon = MapServer.ROOT_ULLON + (top_right_long * tilesizelon);
        raster_lr_lat = MapServer.ROOT_ULLAT - (bottom_left_lat * tilesizelat);

        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

    //helper method that takes d x and y and returns the string name
    private String getimg(int d, int x, int y) {
        return "d" + d + "_x" + x + "_y" + y + ".png";
    }



}
