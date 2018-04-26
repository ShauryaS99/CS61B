import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private double[][] energyMatrix;
    private Picture currentPicture;
    private double[][] minEnergyMatrix;
    private double minEnergyPath;

    public SeamCarver(Picture picture) {
        currentPicture = new Picture(picture);
        energyMatrix = new double[currentPicture.height()][currentPicture.width()];
        for (int w = 0; w < currentPicture.width(); w++) {
            for (int h = 0; h < currentPicture.height(); h++) {
                energyMatrix[h][w] = energy(w, h);
            }
        }
        minEnergyMatrix = minEnergyMatrix();
        minEnergyPath = getMinEnergyPath();
    }

    public Picture picture() {
        Picture newPicture = currentPicture;
        currentPicture = new Picture(currentPicture);
        return newPicture;
    }

    public int width() {
        return currentPicture.width();
    }

    public int height() {
        return currentPicture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new java.lang.IndexOutOfBoundsException("Not a valid index");
        }

        double xGradient = 0;
        double yGradient = 0;

        if (width() == 1) {
            xGradient = 0;
        }
        else {
            //calculating x-gradient for all cases
            if (x == 0) {
                Color rightRGBx = currentPicture.get(x + 1, y);
                Color leftRGBx = currentPicture.get(width() - 1, y);
                xGradient = energyHelper(leftRGBx, rightRGBx);

            } else if (x == width() - 1) {
                Color leftRGBx = currentPicture.get(x - 1, y);
                Color rightRGBx = currentPicture.get(0, y);
                xGradient = energyHelper(leftRGBx, rightRGBx);
            } else {
                Color rightRGBx = currentPicture.get(x + 1, y);
                Color leftRGBx = currentPicture.get(x - 1, y);
                xGradient = energyHelper(leftRGBx, rightRGBx);
            }
        }
        //calculating y-gradient for all cases
        if (height() == 1) {
            yGradient = 0;
        }
        else {
            if (y == 0) {
                Color downRGBy = currentPicture.get(x, y + 1);
                Color upRGBy = currentPicture.get(x, height() - 1);
                yGradient = energyHelper(downRGBy, upRGBy);
            } else if (y == height() - 1) {
                Color upRGBy = currentPicture.get(x, y - 1);
                Color downRGBy = currentPicture.get(x, 0);
                yGradient = energyHelper(downRGBy, upRGBy);
            } else {
                Color downRGBy = currentPicture.get(x, y + 1);
                Color upRGBy = currentPicture.get(x, y - 1);
                yGradient = energyHelper(downRGBy, upRGBy);
            }
        }


        return xGradient + yGradient;
    }

    private double energyHelper(Color a, Color b) {
        double diffRed = (a.getRed() - b.getRed()) * (a.getRed() - b.getRed());
        double diffGreen = (a.getGreen() - b.getGreen()) * (a.getGreen() - b.getGreen());
        double diffBlue = (a.getBlue() - b.getBlue()) * (a.getBlue() - b.getBlue());
        double gradient = diffRed + diffGreen + diffBlue;
        return gradient;
    }

    private double[][] minEnergyMatrix() {
        double[][] minMatrix = new double[energyMatrix.length][energyMatrix[0].length];
        for (int h = 0; h < energyMatrix.length; h++) {
            for (int w = 0; w < energyMatrix[0].length; w++) {
                if (h == 0) {
                    minMatrix [h][w] = energyMatrix[h][w];
                } else {
                    if (w == 0) {
                        minMatrix [h][w] = energyMatrix[h][w]
                                + Math.min(minMatrix [h - 1][w], minMatrix [h - 1][w + 1]);
                    } else if (w == energyMatrix[0].length - 1) {
                        minMatrix [h][w] = energyMatrix[h][w]
                                + Math.min(minMatrix [h - 1][w], minMatrix [h - 1][w - 1]);
                    } else {
                        minMatrix [h][w] = energyMatrix[h][w]
                                + Math.min(Math.min(minMatrix [h - 1][w - 1], minMatrix [h - 1][w]),
                                        minMatrix [h - 1][w + 1]);
                    }
                }
            }
        }
        return minMatrix;
    }

    private double getMinEnergyPath() {
        int lastRow = minEnergyMatrix.length - 1;
        double[] tempArray = new double[minEnergyMatrix[lastRow].length];
        System.arraycopy(minEnergyMatrix[lastRow], 0, tempArray, 0,
                minEnergyMatrix[lastRow].length);
        Arrays.sort(tempArray);
        double minPath = tempArray[0];
        return minPath;
    }


    public int[] findVerticalSeam() {
        int width = minEnergyMatrix[0].length;
        int height = minEnergyMatrix.length;
        int[] verticalSeam = new int[height];
        int numCol = 0;
        for (int h = height - 1; h >= 0; h--) {
            for (int w = 0; w < width; w++) {
                if (h == height - 1) {
                    if (minEnergyMatrix[h][w] == minEnergyPath) {
                        verticalSeam[h] = w;
                        numCol = w;
                        w = width;
                    }
                } else {
                    double energyLeft = 999999999;
                    double energyRight = 999999999;
                    if (numCol != 0) {
                        energyLeft = minEnergyMatrix[h][numCol - 1];
                    }
                    if (numCol != width - 1) {
                        energyRight = minEnergyMatrix[h][numCol + 1];
                    }
                    double energyMid = minEnergyMatrix[h][numCol];
                    double minEnergy = Math.min(Math.min(energyLeft, energyMid), energyRight);
                    if (energyLeft == minEnergy) {
                        numCol -= 1;
                        verticalSeam[h] = numCol;
                    } else if (energyMid == minEnergy) {
                        verticalSeam[h] = numCol;
                    } else {
                        numCol += 1;
                        verticalSeam[h] = numCol;
                    }

                }
            }
        }
        return verticalSeam;
    }

    public int[] findHorizontalSeam() {
        energyMatrix = tranpose(energyMatrix);
        minEnergyMatrix = minEnergyMatrix();
        minEnergyPath = getMinEnergyPath();
        int[] horizontalSeam = findVerticalSeam();
        energyMatrix = tranpose(energyMatrix);
        energyMatrix = tranpose(energyMatrix);
        return horizontalSeam;
    }

    //@source https://stackoverflow.com/questions/15449711/
    private static double[][] tranpose(double[][] matrix) {
        double[][] temp = new double[matrix[0].length][matrix.length];
        int tempHeight = matrix.length;
        int tempWidth = matrix[0].length;
        for (int i = 0; i < tempHeight; i++) {
            for (int j = 0; j < tempWidth; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;

    }

    public void removeVerticalSeam(int[] seam) {
        if (!checkSeam(true, seam)) {
            throw new java.lang.IllegalArgumentException("Not a valid seam");
        }
        SeamRemover.removeVerticalSeam(currentPicture, seam);

    }

    public void removeHorizontalSeam(int[] seam) {
        if (!checkSeam(false, seam)) {
            throw new java.lang.IllegalArgumentException("Not a valid seam");
        }
        SeamRemover.removeHorizontalSeam(currentPicture, seam);
    }

    private boolean checkSeam(boolean vert, int[] seam) {
        if (vert) {
            if (seam.length != height()) {
                return false;
            }
        } else {
            if (seam.length != width()) {
                return false;
            }
        }
        for (int i = 0; i < seam.length - 1; i++) {
            double indexFirst = seam[i];
            double indexNext = seam[i + 1];
            if (Math.abs(indexFirst - indexNext) > 1) {
                return false;
            }

        }
        return true;
    }
}
