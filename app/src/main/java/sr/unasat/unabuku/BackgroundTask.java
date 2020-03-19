package sr.unasat.unabuku;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.widget.Toast;

import sr.unasat.unabuku.Database.UnaBukuDAO;

public class BackgroundTask extends AsyncTask<ContentValues, Void, Boolean> {
    Context ctx;
    UnaBukuDAO unaBukuDAO;

    BackgroundTask(Context ctx) {
        this.ctx = ctx;
        unaBukuDAO = new UnaBukuDAO(ctx);
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(ctx, "De gebruiker word toegevoegd", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected Boolean doInBackground(ContentValues... contentValues) {
        try {
            unaBukuDAO.insertOneUser(contentValues[0]);
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(ctx, "Gebruiker is toegevoegd", Toast.LENGTH_SHORT).show();

            // navigate user to the receipts
            Intent intent = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(intent);
        } else {
            Toast.makeText(ctx, "gebruiker niet toegevoegd", Toast.LENGTH_SHORT).show();
        }
    }
}
