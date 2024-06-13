package AmazeingGui.FileIO;

import AmazeingGui.MazeData.Coords;
import AmazeingGui.Exceptions.*;
import AmazeingGui.MazeData.MazeBrowse;

import java.io.*;
import java.util.ArrayList;

public class MazeFileReader {
    public static boolean isFileBinary(File file) throws IOException{
        InputStream inputStream = new FileInputStream(file);

        byte[] buffer = new byte[4];

        if(inputStream.read(buffer) != 4)
            return false;

        inputStream.close();

        return buffer[3] == 0x52
                && buffer[2] == 0x52
                && buffer[1] == 0x42
                && buffer[0] == 0x43;

    }

    public synchronized static void readTxtToMazeData(File file) throws IOException, MazeException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        Coords entry = null;
        Coords exit = null;
        boolean solveFlag = false;
        int height = 0;
        int width;
        String line;

        //Check first line
        line = reader.readLine();

        if(line == null)
            throw new MazeTooSmallException("Podano pusty plik!");

        width = line.length();

        ArrayList<byte[]> maze = new ArrayList<>();

        byte[] temp;

        while(line != null)
        {
            temp = new byte[width];

            height++;

            if(line.length() != width)
                throw new MazeInconsistentWidthException("Błędna długość wiersza w linii " + height);

            for(int i = 0; i < line.length(); i++)
            {
                switch(line.charAt(i))
                {
                    case 'X':
                        temp[i] = MazeBrowse.Wall;
                        break;

                    case 'P':
                        if(entry != null)
                            throw new MazeDoubleEntryException("Znaleziono dwa wejścia w labiryncie!");

                        entry = new Coords(i, height-1);

                        if(i == 0 || i == width - 1)
                            temp[i] = MazeBrowse.Wall;
                        else
                            temp[i] = MazeBrowse.Path;

                        break;

                    case 'K':
                        if(exit != null)
                            throw new MazeDoubleExitException("Znaleziono dwa wyjścia w labiryncie!");

                        exit = new Coords(i, height-1);

                        if(i == 0 || i == width - 1)
                            temp[i] = MazeBrowse.Wall;
                        else
                            temp[i] = MazeBrowse.Path;

                        break;

                    case ' ':
                        if(i == 0 || i == width - 1)
                            throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + height + ")");

                        temp[i] = MazeBrowse.Path;

                        break;

                    case 'R':
                        if(i == 0 || i == width - 1)
                            throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + height + ")");

                        temp[i] = MazeBrowse.Route;

                        solveFlag = true;

                        break;

                    default:
                        throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + height + "): " + line.charAt(i));
                }
            }

            maze.add(temp);

            line = reader.readLine();
        }

        reader.close();

        if(height < 3 || width < 3)
            throw new MazeTooSmallException("Rozmiar labiryntu jest za mały! (" + width + "x" + height + ")");

        //Sprawdzenie ostatniej i pierwszej linii

        for (int i = 0; i < width; i++)
        {
            if(maze.getFirst()[i] == MazeBrowse.Path) {
                if(entry != null && exit != null) {
                    if (entry.x != i && exit.x != i)
                        throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + 0 + ")");
                    else
                        maze.getFirst()[i] = MazeBrowse.Wall;
                }
                else
                    throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + 0 + ")");
            }
        }

        for (int i = 0; i < width; i++)
        {
            if(maze.getLast()[i] == MazeBrowse.Path) {
                if(entry != null && exit != null) {
                    if (entry.x != i && exit.x != i)
                        throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + 0 + ")");
                    else
                        maze.getLast()[i] = MazeBrowse.Wall;
                }
                else
                    throw new MazeIncorrectCharException("Błędny znak w pliku w pozycji (" + i + ", " + 0 + ")");
            }
        }

        //Arraylist do 2d tablicy

        byte[][] finalArray = new byte[maze.size()][width];

        for (int i = 0; i < maze.size(); i++) {
            finalArray[i] = maze.get(i);
        }

        MazeBrowse.getInstance().changeMaze(finalArray, entry, exit, file.getName());
        MazeBrowse.getInstance().setSolved(solveFlag);
    }

    public synchronized static void readBinToMazeData(File file) throws IOException, MazeException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        BinaryFileHeader header = new BinaryFileHeader(raf);

        if(header.esc != 0x1B)
            throw new MazeIncorrectCharException("Błędny znak w pozycji: (" + 0 + ", " + 0 + ")");

        if(header.rows < 3 || header.columns < 3)
            throw new MazeTooSmallException("Labirynt za mały!");

        byte[][] maze = new byte[header.rows][header.columns];

        short currCol = 0;
        short currRow = 0;
        CodeWord currWord;

        for(int i = 0; i < header.counter; i++)
        {
            currWord = new CodeWord(raf.readByte(), raf.readByte(), raf.readByte());

            for(int j = 0; j < currWord.counter + 1; j++)
            {
                if(currWord.value == header.path)
                    maze[currRow][currCol] = MazeBrowse.Path;
                else if(currWord.value == header.wall)
                    maze[currRow][currCol] = MazeBrowse.Wall;
                else
                    throw new MazeIncorrectCharException("Błędny znak w pliku binarnym: (" + currCol + ", " + currRow + ") - \"" + (char) (currWord.value & 0xFF) + "\"");

                currCol++;

                if(currCol == header.columns)
                {
                    currCol = 0;
                    currRow++;
                }
            }
        }

        Coords entry = new Coords(header.entryX - 1, header.entryY - 1);
        Coords exit = new Coords(header.exitX - 1, header.exitY - 1);

        if(entry.x == 0 || entry.x == header.columns - 1 || entry.y == 0 || entry.y == header.rows - 1)
            maze[entry.y][entry.x] = MazeBrowse.Wall;

        if(exit.x == 0 || exit.x == header.columns - 1 || exit.y == 0 || exit.y == header.rows - 1)
            maze[exit.y][exit.x] = MazeBrowse.Wall;

        for (int i = 0; i < header.columns - 1; i++)
        {
            if(maze[0][i] == header.path) {
                throw new MazeIncorrectCharException("Błędny znak w labiryncie w pozycji (" + i + ", " + 0 + ")");
            }
        }

        for (int i = 0; i < header.columns - 1; i++) {
            if (maze[header.rows - 1][i] == header.path) {
                throw new MazeIncorrectCharException("Błędny znak w labiryncie w pozycji (" + i + ", " + (header.rows - 1) + ")");
            }
        }

        for (int i = 0; i < header.rows - 1; i++) {
            if (maze[i][0] == header.path)
                throw new MazeIncorrectCharException("Błędny znak w labiryncie w pozycji (" + 0 + ", " + i + ")");

            if (maze[i][header.columns - 1] == header.path)
                throw new MazeIncorrectCharException("Błędny znak w labiryncie w pozycji (" + (header.columns - 1) + ", " + i + ")");
        }

        raf.close();

        MazeBrowse.getInstance().changeMaze(maze, entry, exit, file.getName());
    }
}
