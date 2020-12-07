package com.bentechprotv.android.comptesurleweb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MesComptesActivity extends AppCompatActivity {

    Cursor cur;
    SQLiteDatabase db;
    LinearLayout layNaviguer, layRecherche;
    EditText _txtSiteWeb, _txtApplication,_txtCompte,_txtPassword,_txtRechercheSiteWeb;
    ImageButton _btnRecherche;
    Button _btnFirst,_btnPrevious,_btnNext,_btnLast;
    Button _btnAdd,_btnUpdate,_btnDelete;
    Button _btnCancel,_btnSave;
    int op = 0;
    String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_comptes);

        layNaviguer = (LinearLayout) findViewById(R.id.layNaviguer);
        layRecherche= (LinearLayout) findViewById(R.id.layRecherche);

        _txtRechercheSiteWeb = (EditText) findViewById(R.id.txtRechercheSiteWeb);
        _txtSiteWeb = (EditText) findViewById(R.id.txtSiteWeb);
        _txtApplication = (EditText) findViewById(R.id.txtApplication);
        _txtCompte = (EditText) findViewById(R.id.txtCompte);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);

        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnUpdate = (Button) findViewById(R.id.btnUpdate);
        _btnDelete = (Button) findViewById(R.id.btnDelete);

        _btnFirst = (Button) findViewById(R.id.btnFirst);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);
        _btnLast = (Button) findViewById(R.id.btnLast);

        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);

        _btnRecherche = (ImageButton) findViewById(R.id.btnRecherche);

        // ouverture d'une connexion vers la base de données
        db = openOrCreateDatabase("ComptesWeb",MODE_PRIVATE,null);
        // Création de la table comptes
        db.execSQL("CREATE TABLE IF NOT EXISTS COMPTES (id integer primary key autoincrement, siteweb VARCHAR, application VARCHAR, userid VARCHAR, pwd VARCHAR);");

        layNaviguer.setVisibility(View.INVISIBLE);
        _btnSave.setVisibility(View.INVISIBLE);
        _btnCancel.setVisibility(View.INVISIBLE);

        _btnRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur = db.rawQuery("select * from comptes where siteweb like ?", new String[]{"%" + _txtRechercheSiteWeb.getText().toString() + "%"});
                try {
                    cur.moveToFirst();
                    _txtSiteWeb.setText(cur.getString(1));
                    _txtApplication.setText(cur.getString(2));
                    _txtCompte.setText(cur.getString(3));
                    _txtPassword.setText(cur.getString(4));
                    if (cur.getCount() == 1){
                        layNaviguer.setVisibility(View.INVISIBLE);
                    } else {
                        layNaviguer.setVisibility(View.VISIBLE);
                        _btnPrevious.setEnabled(false);
                        _btnNext.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun résultat.",Toast.LENGTH_SHORT).show();
                    _txtSiteWeb.setText("");
                    _txtApplication.setText("");
                    _txtCompte.setText("");
                    _txtPassword.setText("");
                    layNaviguer.setVisibility(View.INVISIBLE);
                }
            }
        });

        _btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToFirst();
                    _txtSiteWeb.setText(cur.getString(1));
                    _txtApplication.setText(cur.getString(2));
                    _txtCompte.setText(cur.getString(3));
                    _txtPassword.setText(cur.getString(4));
                    _btnPrevious.setEnabled(false);
                    _btnNext.setEnabled(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun compte n'est existant.",Toast.LENGTH_SHORT).show();

                }
            }
        });
        _btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToLast();
                    _txtSiteWeb.setText(cur.getString(1));
                    _txtApplication.setText(cur.getString(2));
                    _txtCompte.setText(cur.getString(3));
                    _txtPassword.setText(cur.getString(4));
                    _btnPrevious.setEnabled(true);
                    _btnNext.setEnabled(false);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"aucun compte n'est existant.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToNext();
                    _txtSiteWeb.setText(cur.getString(1));
                    _txtApplication.setText(cur.getString(2));
                    _txtCompte.setText(cur.getString(3));
                    _txtPassword.setText(cur.getString(4));
                    _btnPrevious.setEnabled(true);
                    if (cur.isLast()){
                        _btnNext.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

        _btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cur.moveToPrevious();
                    _txtSiteWeb.setText(cur.getString(1));
                    _txtApplication.setText(cur.getString(2));
                    _txtCompte.setText(cur.getString(3));
                    _txtPassword.setText(cur.getString(4));
                    _btnNext.setEnabled(true);
                    if (cur.isFirst()){
                        _btnPrevious.setEnabled(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });

          _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;
                _txtSiteWeb.setText("");
                _txtApplication.setText("");
                _txtCompte.setText("");
                _txtPassword.setText("");
                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                layNaviguer.setVisibility(View.INVISIBLE);
                layRecherche.setVisibility(View.INVISIBLE);
            }
        });

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tester si les champs ne sont pas vides
                try {
                    x = cur.getString(0);
                    op = 2;

                    _btnSave.setVisibility(View.VISIBLE);
                    _btnCancel.setVisibility(View.VISIBLE);

                    _btnDelete.setVisibility(View.INVISIBLE);
                    _btnUpdate.setEnabled(false);
                    _btnAdd.setVisibility(View.INVISIBLE);

                    layNaviguer.setVisibility(View.INVISIBLE);
                    layRecherche.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Sélectionnez un compte puis appyuer sur le bouton de modification",Toast.LENGTH_SHORT).show();
                }

            }
        });

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (op == 1){
                    // insertion
                    db.execSQL("insert into comptes (siteweb,application,userid,pwd) values (?,?,?,?);", new String[] {_txtSiteWeb.getText().toString(), _txtApplication.getText().toString(),_txtCompte.getText().toString(),_txtPassword.getText().toString()});
                } else if (op == 2) {
                    // Mise à jour
                    db.execSQL("update comptes set siteweb=?, application=?, userid=?, pwd=? where id=?;", new String[] {_txtSiteWeb.getText().toString(), _txtApplication.getText().toString(),_txtCompte.getText().toString(),_txtPassword.getText().toString(),x});
                }

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);
                _btnRecherche.performClick();
                layRecherche.setVisibility(View.VISIBLE);
            }
        });
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             op = 0;

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                _btnUpdate.setVisibility(View.VISIBLE);
                _btnDelete.setVisibility(View.VISIBLE);

                _btnAdd.setVisibility(View.VISIBLE);
                _btnAdd.setEnabled(true);
                _btnUpdate.setEnabled(true);

                layRecherche.setVisibility(View.VISIBLE);
            }
        });


        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                  x=  cur.getString(0);
                  AlertDialog dial = MesOptions();
                  dial.show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Sélectionner un compte puis appyuer sur le bouton de suppresssion",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });



    }

    private AlertDialog MesOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Est ce que vous voulez supprimer ce compte?")
                .setIcon(R.drawable.validate)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.execSQL("delete from comptes where id=?;",new String[] {cur.getString(0)});
                        _txtSiteWeb.setText("");
                        _txtApplication.setText("");
                        _txtCompte.setText("");
                        _txtPassword.setText("");
                        layNaviguer.setVisibility(View.INVISIBLE);
                        cur.close();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return MiDia;
    }
}