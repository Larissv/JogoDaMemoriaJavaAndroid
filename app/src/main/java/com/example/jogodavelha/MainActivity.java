package com.example.jogodavelha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private int jogadorAtual;
    private final int[][] posicao = new int[3][3];
    private final Button[] btn = new Button[9];
    private String ganhador;
    private String jogador1;
    private String jogador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBotoes();
        setupCliks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_menu_novo_jogo) {
           limpar();
            AlertDialog.Builder segundojogador = dialogNomeJogador(
                    "Digite o nome do jogador 1: ", "JOGADOR 1: ", jogador1);
            segundojogador.create();
           segundojogador.show();
            AlertDialog.Builder primeirojogador = dialogNomeJogador(
                    "Digite o nome do jogador 2: ", "JOGADOR 2: ", jogador2);
            primeirojogador.create();
            primeirojogador.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private AlertDialog.Builder dialogNomeJogador(String message, String title, String jogador2) {
        final EditText editText2 = new EditText(this);
        AlertDialog.Builder segundojogador = new AlertDialog.Builder(this);
        segundojogador.setMessage(message);
        segundojogador.setTitle(title);
        segundojogador.setView(editText2);
        segundojogador.setPositiveButton("OK", (dialogInterface, i) -> editText2.getText());
        return segundojogador;
    }

    public void jogada(Button button, int x, int y) {
        button.setEnabled(true);

        if (jogadorAtual == 1) {
            posicao[x][y] = 1;
            button.setText("X");
            jogadorAtual = 2;
            ganhador = jogador1;
            checarJogada(1);
        } else {
            posicao[x][y] = 2;
            button.setText("O");
            jogadorAtual = 1;
            ganhador = jogador2;
            checarJogada(2);
        }
    }

    public boolean vitoria(int x) {
        if (verificaPosicoesHorizontalVertical(x))
            return true;
        return verificaPosicoesDiagonais(x);
    }

    private boolean verificaPosicoesDiagonais(int x) {
        if(posicao[0][0] == x && posicao[1][1] == x && posicao[2][2] == 0){
            return true;
        }
        return posicao[0][2] == x && posicao[1][1] == x && posicao[2][0] == x;
    }

    private boolean verificaPosicoesHorizontalVertical(int x) {
        for (int i = 0; i < posicao.length; i++) {
            if (posicao[i][0] == x && posicao[i][1] == x && posicao[i][2] == x){
                return true;
            }
            if (posicao[0][i] == x && posicao[1][i] == x && posicao[2][i] == x) {
                return true;
            }
        }
        return false;
    }

    public void checarJogada(int x) {
        if (vitoria(x)) {
            AlertDialog.Builder alertaVenceu = new AlertDialog.Builder(this);
            alertaVenceu.setTitle("VITÓRIA!");
            alertaVenceu.setMessage("O jogador " + ganhador + " venceu!");
            alertaVenceu.setPositiveButton("OK", null);
            alertaVenceu.create();
            alertaVenceu.show();
            fimDeJogo();
        }
    }

    public void fimDeJogo() {
        for (int i = 0; i < 9; i++) {
            btn[i].setEnabled(false);
        }
    }

    public void limpar() {
        for (int i = 0; i < 9; i++) {
            btn[i].setEnabled(true);
            btn[i].setText("");
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                posicao[x][y] = 0;
            }
        }
        jogadorAtual = 0;
        jogador1 = " ";
        jogador2 = " ";
        ganhador = " ";
    }

    private void setupCliks() {
        btn[0].setOnClickListener(view -> jogada(btn[0], 0, 0));
        btn[1].setOnClickListener(view -> jogada(btn[1], 0, 1));
        btn[2].setOnClickListener(view -> jogada(btn[2], 0, 2));
        btn[3].setOnClickListener(view -> jogada(btn[3], 1, 0));
        btn[4].setOnClickListener(view -> jogada(btn[4], 1, 1));
        btn[5].setOnClickListener(view -> jogada(btn[5], 1, 2));
        btn[6].setOnClickListener(view -> jogada(btn[6], 2, 0));
        btn[7].setOnClickListener(view -> jogada(btn[7], 2, 1));
        btn[8].setOnClickListener(view -> jogada(btn[8], 2, 3));
    }

    private void setupBotoes() {
        btn[0] = findViewById(R.id.btn_1);
        btn[1] = findViewById(R.id.btn_2);
        btn[2] = findViewById(R.id.btn_3);
        btn[3] = findViewById(R.id.btn_4);
        btn[4] = findViewById(R.id.btn_5);
        btn[5] = findViewById(R.id.btn_6);
        btn[6] = findViewById(R.id.btn_7);
        btn[7] = findViewById(R.id.btn_8);
        btn[8] = findViewById(R.id.btn_9);
    }
}