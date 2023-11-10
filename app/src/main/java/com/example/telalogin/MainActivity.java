package com.example.telalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText edEmail, edSenha;
    Button bt, btLogar, btRecupera;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.editTextEmail);
        edSenha = findViewById(R.id.editTextSenha);
        btLogar = findViewById(R.id.buttonLogar);
        btRecupera = findViewById(R.id.buttonRec);

        bt = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        btRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(MainActivity.this, "Email de recuperação de senha enviado.", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               String email = edEmail.getText().toString();
               String senha = edSenha.getText().toString();

               mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

                           if (usuario != null) {
                               if (usuario.isEmailVerified()) {
                                   // Se o e-mail estiver verificado, envie para a outra tela
                                   Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                   i.putExtra("email_digitado", email);
                                   i.putExtra("senha_digitada", senha);

                                   startActivity(i);
                               } else {
                                   Toast.makeText(MainActivity.this, "Usuário não verificado. Enviando e-mail de verificação.", Toast.LENGTH_SHORT).show();
                                   usuario.sendEmailVerification();
                               }
                           } else {
                               // Trate o caso em que o usuário é nulo
                               Toast.makeText(MainActivity.this, "Erro ao obter informações do usuário.", Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           Toast.makeText(MainActivity.this, "Erro ao logar.", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
        });



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edEmail.getText().toString();
                final String senha = edSenha.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Usuário criado", Toast.LENGTH_SHORT).show();

                            // Agora, após criar o usuário com sucesso, tenta fazer login
                            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

                                        if (usuario != null) {
                                            if (usuario.isEmailVerified()) {
                                                // Se o e-mail estiver verificado, envie para a outra tela
                                                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                                i.putExtra("email_digitado", email);
                                                i.putExtra("senha_digitada", senha);

                                                startActivity(i);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Usuário não verificado. Enviando e-mail de verificação.", Toast.LENGTH_SHORT).show();
                                                usuario.sendEmailVerification();
                                            }
                                        } else {
                                            // Trate o caso em que o usuário é nulo
                                            Toast.makeText(MainActivity.this, "Erro ao obter informações do usuário.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Erro ao logar após criar usuário.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Usuário NÃO foi criado.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}