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
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //sets all params
        double rasterUlLon = params.get("ullon");
        double rasterUlLat = params.get("ullat");
        double rasterLrLon = params.get("lrlon");
        double rasterLrLat = params.get("lrlat");
        //checks if query_success
        boolean querySuccess = true;
        if ((rasterLrLon < MapServer.ROOT_ULLON) || (rasterUlLon > MapServer.ROOT_LRLON
                || (rasterUlLat < MapServer.ROOT_LRLAT)
                || (rasterLrLat > MapServer.ROOT_ULLAT))) {
            querySuccess = false;
        }
        double rasterWidth = abs(rasterUlLon - rasterLrLon);
        //double raster_height = abs(rasterUlLat - rasterLrLat);
        //zoomlevel
        double londpp = (rasterWidth) * 288200 / params.get("w");
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
        int nearUlLon = (int) abs((MapServer.ROOT_ULLON - rasterUlLon) / tilesizelon);
        // long value of top left (A)
        double imgTlLong = MapServer.ROOT_ULLON + (nearUlLon * tilesizelon);
        int topRightLong = (int) (Math.pow(2, depth) - abs((MapServer.ROOT_LRLON - rasterLrLon)
                / tilesizelon)) + 1;
        int numxtiles = abs(topRightLong - nearUlLon);

        //iterates through lat # of nearest box in top left (Bi)
        int nearUlLat = (int) abs((MapServer.ROOT_ULLAT - rasterUlLat) / tilesizelat);
        // lat value of top left (B)
        double imgTlLat = MapServer.ROOT_ULLAT - (nearUlLat * tilesizelat);
        int bottomLeftLat = (int) (Math.pow(2, depth) - abs((MapServer.ROOT_LRLAT - rasterLrLat)
                / tilesizelat)) + 1;
        int numytiles = abs(bottomLeftLat - nearUlLat);
        String[][] renderGrid = new String[numytiles][numxtiles];

        for (int a = 0; a < numytiles; a++) { // a is iterating through vertically
            for (int b = 0; b < numxtiles; b++) { // b is iterating through horizontally
                String imgname = getimg(depth, nearUlLon + b, nearUlLat + a);
                renderGrid[a][b] = imgname;
            }
        }
        rasterUlLon = imgTlLong;
        rasterUlLat = imgTlLat;
        rasterLrLon = MapServer.ROOT_ULLON + (topRightLong * tilesizelon);
        rasterLrLat = MapServer.ROOT_ULLAT - (bottomLeftLat * tilesizelat);

        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterUlLon);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("raster_lr_lon", rasterLrLon);
        results.put("raster_lr_lat", rasterLrLat);
        results.put("depth", depth);
        results.put("query_success", querySuccess);
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

    //helper method that takes d x and y and returns the string name
    private String getimg(int d, int x, int y) {
        return "d" + d + "_x" + x + "_y" + y + ".png";
    }



}
