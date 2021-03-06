package blog.ugurcan.firedroid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by ugurcan on 28.12.2017.
 */
public abstract class FireDroidActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getName(), "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getName(), "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getName(), "onResume()");

        FireDroid.setCurrentActivity(this);
        FireDroid.setListeners(this);

        FireDroid._auth().addAuthStateListener();
        FireDroid._db().startSubscriptions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getName(), "onPause()");

        FireDroid._auth().removeAuthStateListener();
        FireDroid._db().endSubscriptions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getName(), "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getName(), "onDestroy()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FireDroid._auth().handleLoginResult(requestCode, resultCode, data);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    /*
     * DIALOG HELPERS
     */
    public void showProgress(String message) {
        hideDialog();

        dialog = new ProgressDialog(this);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showMessage(String title, String message) {
        hideDialog();

        dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
