package com.company;
import java.io.*;

public class OS {


    //Core data structures for Operating System
    private char [][]memory= new char[100][4];
    private char []buffer= new char[40];
    private char []R=new char[4];
    private char []IR=new char[4];
    private int IC;
    private int T;
    private int SI;

    //Non core data structures
    private int memory_used;
    private int data_card_skip=0;
    private String input_file;
    private String output_file;
    private  FileReader input;
    private BufferedReader fread;
    private  FileWriter output;
    private BufferedWriter fwrite;

    public OS(String file,String output){
        this.input_file=file;
        this.SI=0;
        try {
            this.input = new FileReader(file);
            this.fread= new BufferedReader(input);
            this.output=new FileWriter(output);
            //this.fwrite= new BufferedWriter(this.output);
            //fwrite.write("Svsndjd");
            //fwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LOAD()
    {
        int flag=0;
        String line;
        try {

                while((line=fread.readLine()) != null)
                {
                    buffer=line.toCharArray();
                    if(buffer[0]=='$'&& buffer[1]=='A'&&buffer[2]=='M'&& buffer[3]=='J') {
                        System.out.println("Program card detected");
                        init();
                        continue;
                    }
                    else if(buffer[0]=='$'&& buffer[1]=='D'&&buffer[2]=='T'&& buffer[3]=='A')
                    {
                        System.out.println("Data card detected");
                        execute();
                        flag=2;
                        continue;
                    }
                    else if(buffer[0]=='$'&& buffer[1]=='E'&&buffer[2]=='N'&& buffer[3]=='D')
                    {
                        System.out.println("END card detected");
                        output.write("\n\n\n");
                        print_memory();
                        continue;
                    }
                    if(memory_used==100)
                    {   //abort;
                        System.out.println("Abort due to exceed memory usage");
                    }
                   // System.out.println(line.length());

                        //System.out.println("Mai load hora");
                        System.out.println("ur program starts here");
                        for (int i = 0; i < line.length();) {
                            //System.out.println(buffer[i]);
                            memory[memory_used][i%4]=buffer[i];
                            i++;
                            if(i%4==0)
                                memory_used++;
                            }


                }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(memory_used);
        //print_memory();
    }
    private void execute(){
        while(1<2)
        {
            if(IC==100)
                break;
             IR[0]=memory[IC][0];
             IR[1]=memory[IC][1];
             IR[2]=memory[IC][2];
             IR[3]=memory[IC][3];

             IC++;
             if(IR[0]=='L' && IR[1]=='R')
             {
                 String line = new String(IR);
                 //System.out.println(line);
                 //System.out.println(line.substring(2));
                 int num=Integer.parseInt(line.substring(2));
                R[0]=memory[num][0];
                R[1]=memory[num][1];
                R[2]=memory[num][2];
                R[3]=memory[num][3];
             }
            else if(IR[0]=='S' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                memory[num][0]=R[0];
                memory[num][1]=R[1];
                memory[num][2]=R[2];
                memory[num][3]=R[3];
            }
            else if(IR[0]=='C' && IR[1]=='R')
            {
                String line = new String(IR);
                int num=Integer.parseInt(line.substring(2));
                if(memory[num][0]==R[0]&& memory[num][1]==R[1]&& memory[num][2]==R[2]&& memory[num][3]==R[3])
                {
                    T=1;
                }
                else
                {
                    T=0;

                }
            }
            else if(IR[0]=='B' && IR[1]=='T')
            {
                if(T==1)
                {
                    String line = new String(IR);
                    int num=Integer.parseInt(line.substring(2));
                    IC=num;
                    T=0;
                }
            }
            else if(IR[0]=='G' && IR[1]=='D')
            {
                SI=1;
                masterMode();
            }
            else if(IR[0]=='P' && IR[1]=='D')
            {
                SI=2;
                masterMode();
            }
            else if(IR[0]=='H')
            {
                SI=3;
                masterMode();
            }
        }
    }

    private void masterMode() {
         int i=this.SI;
        if(i==1)
        {
           Read();
        }
        else if(i==2)
        {
            Write();
        }
        else if(i==3)
        {

        }
        SI=0;
    }

    private void Write() {
        IR[3]='0';
        String line = new String(IR);
        int num=Integer.parseInt(line.substring(2));
        String t,total="";
        for(int i=0;i<10;i++)
        {
            t=new String(memory[num+i]);
            total=total.concat(t);
        }
        System.out.println(total+"In write");
        try {
            output.write(total);
            output.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Read() {
        int flag=0;
        IR[3]='0';
        String line = new String(IR);

        int num=Integer.parseInt(line.substring(2));


        try {
            line=fread.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer=line.toCharArray();
                for (int i = 0; i < line.length();) {
                    //System.out.println(buffer[i]);
                    memory[num][(i%4)]=buffer[i];
                    i++;
                    if(i%4==0)
                        num++;
                }
        }

    public void init(){
        memory_used=0;
        memory=null;
        memory= new char[100][4];
        data_card_skip=0;
        T=0;
        IC=0;
    }
    public void print_memory(){
        for(int i=0;i<100;i++) {
            System.out.println("memory["+i+"] "+new String(memory[i]));
        }
    }
}
