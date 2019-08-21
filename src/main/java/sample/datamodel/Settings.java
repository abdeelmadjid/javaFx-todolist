package sample.datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings  {
    private static Settings instance=new Settings();
    private  String fileName = "setting.txt";
    private  boolean dark;
    private  double split;
    private Path path;
    public static Settings getInstance(){
        return instance;
    }


    public  boolean isDark() {
        return dark;
    }

    public  void setDark(boolean dark) {
        this.dark = dark;
    }

    public  double getSplit() {
        return split;
    }

    public  void setSplit(double split) {
        this.split = split;
    }


     Settings() {
       path= Paths.get(fileName);
        loadSettings(path);

    }
    public  void loadSettings(Path path){
        try{
            BufferedReader bufferedReader= Files.newBufferedReader(path);
            String theme=bufferedReader.readLine();
            if(theme.equals("dark")){
                dark=true;
            }else if (theme.equals("light")){
                dark=false;
            }
            split=Double.parseDouble(bufferedReader.readLine());
            bufferedReader.close();



        }catch (Exception notfond){
            saveData(path,"light","0.15");

        }

    }
    public static void saveData(Path path,String theme,String split){
        try {
            BufferedWriter bufferedWriter=Files.newBufferedWriter(path);
            bufferedWriter.write(theme);
            bufferedWriter.newLine();
            bufferedWriter.write(split);
            bufferedWriter.close();
        }catch (Exception notCreate ){

        }
    }
    public  void saveData(){
        String theme;
        String split;
        if (dark){
            theme="dark";
        }else{
            theme="light";
        }
        split=Double.toString(this.split);
//        split=Double.parseDouble(this.split);

        try {
            BufferedWriter bufferedWriter=Files.newBufferedWriter(this.path);
            bufferedWriter.write(theme);
            bufferedWriter.newLine();
            bufferedWriter.write(split);
            bufferedWriter.close();
        }catch (Exception notCreate ){

        }
    }
}
/**
 * the settings in txt file is like{
 *     dark     <<<< dark or light theme
 *     0.2      <<<< any double for splitpane DividerPositions
 * }
 * **/