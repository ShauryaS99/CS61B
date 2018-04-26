import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private double[][] energyMatrix;
    private Picture currentPicture;

    public SeamCarver(Picture picture) {
        currentPicture = picture;
        energyMatrix = new double[currentPicture.height()][currentPicture.width()];
        for (int w = 0; w < currentPicture.width(); w++) {
            for (int h = 0; h < currentPicture.height(); h++) {
                energyMatrix[h][w] = energy(w, h);
            }
        }
    }

    public Picture picture() {
        return currentPicture;
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
        //calculating y-gradient for all cases
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


        return xGradient + yGradient;
    }

    private double energyHelper(Color a, Color b) {
        double diffRed = Math.pow(Math.abs(a.getRed() - b.getRed()), 2);
        double diffGreen = Math.pow(Math.abs(a.getGreen() - b.getGreen()), 2);
        double diffBlue = Math.pow(Math.abs(a.getBlue() - b.getBlue()), 2);
        double gradient = diffRed + diffGreen + diffBlue;
        return gradient;
    }

    public int[] findVerticalSeam() {
        int width = energyMatrix[0].length;
        int height = energyMatrix.length;
        int[] verticalSeam = new int[height];
        double energyLowest = 999999999;
        int numCol = 0;
        boolean firstIteration = true;
        //iterate from bottom row up
        for (int h = height - 1; h >= 0; h--) {
            if (firstIteration) {
                for (int w = 0; w < width; w++) {
                    double energy = energy(w, h);
                    if (energy < energyLowest) {
                        energyLowest = energy;
                        numCol = w;
                        verticalSeam[h] = numCol;
                    }
                }
            } else {
                double energyLeft = 999999999;
                double energyRight = 999999999;
                if (numCol != 0) {
                    energyLeft = energy(numCol - 1, h);
                } else if (numCol != width - 1) {
                    energyRight = energy(numCol + 1, h);
                }
                double energyMid = energy(numCol, h);
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

            firstIteration = false;
        }
        return verticalSeam;
    }

    public int[] findHorizontalSeam() {
        energyMatrix = tranpose(energyMatrix);
        int[] horizontalSeam = findVerticalSeam();
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
