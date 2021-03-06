package com.exercise.controller;

import com.exercise.entities.Carta;
import com.exercise.entities.Mano;
import com.exercise.entities.Mazzo;

import java.util.Scanner;

public class Controller {
    private Scanner input = new Scanner(System.in);
    private Mazzo mazzo;
    private Mano banco;
    private Mano giocatore;

    public void run() {
        System.out.println("Premi invio per iniziare\n");
        if (input.nextLine().equals("")) {
            _game();
            String cmd = "";
            do {
                System.out.println("\n\nVuoi giocare ancora? S/N");
                cmd = input.nextLine();
                switch (cmd.toUpperCase()) {
                    case "S":
                        _game();
                        break;
                    case "N":
                        break;
                }
            } while (!cmd.equalsIgnoreCase("N"));
        } else {
            System.out.println("Comando sconosciuto, il programma verrà terminato...");
            System.exit(-1);
        }
        input.close();
    }

    private void _game() {
        mazzo = new Mazzo();
        banco = new Mano();
        giocatore = new Mano();
        System.out.println(
                            "=============================================================="+
                            "\n\t\t\t\tBLACKJACK JAVA\n"+
                            "=============================================================="+
                            "\nI giocatori ricevono due carte a testa...\n"
        );
        System.out.println(_renderManoIniziale());
        _pescaCarte();
        System.out.println(
                _esito() +
                "\n=============================================================="
        );
    }

    private void _generaManoIniziale() {
        for(int i=0; i<2;i++) {
            banco.aggiungi(mazzo.pesca());
            giocatore.aggiungi(mazzo.pesca());
        }
    }

    private String _calcPunteggio() {
        return  "=============================================================="+
                "\n\t\t\t\tPUNTEGGIO"+
                "\n-BANCO: " + banco.getPunteggio()+"\n-GIOCATORE: " + giocatore.getPunteggio()+
                "\n==============================================================";
    }

    private boolean _haSballato(Mano mano) {
        return mano.getPunteggio()>21;
    }

    private String _esito() {
        if(_haSballato(giocatore))
            return "\nHAI SBALLATO...IL BANCO VINCE!";
        if(_haSballato(banco))
            return "\nBANCO HA SBALLATO...HAI VINTO!";
        if(banco.getPunteggio() < giocatore.getPunteggio())
            return "\nHAI VINTO LA PARTITA";
        else
            return "\nHAI PERSO LA PARTITA";
    }

    private void _pescaCarte() {
        String ris = "";
        do {
            System.out.println("-GIOCATORE \nVuoi pescare una carta? S/N");
            ris = input.nextLine();
            switch (ris.toUpperCase()) {
                case "S":
                    Carta nuovaCarta = mazzo.pesca();
                    System.out.println("Hai pescato: " + nuovaCarta.scheda());
                    giocatore.aggiungi(nuovaCarta);
                    System.out.println(_calcPunteggio());
                    break;
                case "N":
                    System.out.println("Fine turno");
                    break;
                default:
                    System.out.println("Comando sconosciuto");
            }
        } while (!ris.equalsIgnoreCase("N") && !_haSballato(giocatore));

        if (!_haSballato(giocatore)) {
            System.out.println("\n-BANCO");
            if (banco.getPunteggio() < giocatore.getPunteggio()) {
                while (banco.getPunteggio() < giocatore.getPunteggio()) {
                    Carta nuovaCarta = mazzo.pesca();
                    banco.aggiungi(nuovaCarta);
                    System.out.println("BANCO pesca: " + nuovaCarta.scheda());
                    System.out.println(_calcPunteggio());
                }
            } else
                System.out.println("BANCO non pesca");
        }
    }

    private String _renderManoIniziale() {
        _generaManoIniziale();
        return  "-BANCO\n"+banco+"\n"+
                "-GIOCATORE\n"+giocatore+
                _calcPunteggio();
    }
}


