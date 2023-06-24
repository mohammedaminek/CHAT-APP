package com.example.controle;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class writeRead {
    File fichier;
    public writeRead(File fichier) {
        this.fichier = fichier;
    }

    Manager nj;
    void write (Manager m) throws IOException {
        ObjectInputStream entree =null;
        ObjectOutputStream sortie =null;
        boolean flag=false;
        File temporaire = new File( "temp.txt");
        sortie = new ObjectOutputStream(new FileOutputStream(temporaire));
        try {
            entree = new ObjectInputStream(new FileInputStream(fichier));
            nj = (Manager) entree.readObject();
            while (nj != null) {
                if (m.ID.equals(nj.ID)) {
                    sortie.writeObject(m);
                    flag = true;
                } else {
                    sortie.writeObject(nj);
                }
                nj = (Manager) entree.readObject();
            }
        }catch (FileNotFoundException e) {}
        catch (ClassNotFoundException e) {}
        catch (EOFException e) { entree.close();}


        if(!flag) {
            sortie.writeObject(m);

        }
        sortie.close();
        fichier.delete();
        temporaire.renameTo(fichier);
     }
     Set<Manager> Read() throws IOException {
        Manager m;
        Set<Manager> s =new HashSet<Manager>();
        ObjectInputStream entree =new ObjectInputStream(new FileInputStream(fichier));
        try{
            m= (Manager)entree.readObject();
            while(m!=null){
                s.add(m);
                m= (Manager)entree.readObject();
            }
        }catch (EOFException e){
            entree.close();
        } catch (ClassNotFoundException e) {

        }
         return s;
     }
}
