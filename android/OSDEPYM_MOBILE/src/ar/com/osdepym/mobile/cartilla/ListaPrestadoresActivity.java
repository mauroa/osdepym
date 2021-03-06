package ar.com.osdepym.mobile.cartilla;

import java.util.ArrayList;
import java.util.List;

import tareas.TareaBusquedaPrestadores;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ar.com.osdepym.mobile.cartilla.R;
import ar.com.osdepym.mobile.cartilla.adaptadores.AdaptadorEspecialista;
import ar.com.osdepym.mobile.cartilla.dto.FiltroDTO;
import ar.com.osdepym.mobile.cartilla.dto.PrestadorDTO;
import ar.com.osdepym.mobile.cartilla.util.ServiceManager;

/**
 * 
 * @author Andres
 * 
 */
public class ListaPrestadoresActivity extends AbstractListActivity {

	private List<PrestadorDTO> listaDatos = new ArrayList<PrestadorDTO>();

	private FiltroDTO filtro;
	private int titulo;
	ProgressDialog progressDialog;
	TextView listaVacia;
	Tarea tareaBusqueda;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		Intent i = getIntent();

		filtro = (FiltroDTO) i.getSerializableExtra("filtro");
		this.titulo = i.getIntExtra("titulo", R.string.resultado_busqueda);

		new TareaBusquedaPrestadores(filtro.getEspecialidad(), filtro.getProvincia(),
									filtro.getLocalidad(), filtro.getNombreEspecialista(),
									this)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		Log.i("ESTRATEGIA", "Buscando datos desde la base");

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.lista_prestadores);
		Button botonTitulo = (Button) findViewById(R.id.botonResultado);
		botonTitulo.setText(titulo);

		this.listaVacia = (TextView) findViewById(android.R.id.empty);
		this.listaVacia.setVisibility(View.GONE);

	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		
		super.onListItemClick(listView, view, position, id);

		PrestadorDTO obj = (PrestadorDTO) getListAdapter().getItem(position);

		Intent in = new Intent(getApplicationContext(), PrestadorActivity.class);

		in.putExtra("EspecialistaDTO", obj);
		in.putExtra("titulo", this.titulo);
		startActivity(in);
	}

	/**
	 * 
	 * @author Andres
	 * 
	 */
	private class Tarea extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			ServiceManager sm = new ServiceManager();
			try {
				listaDatos = sm.buscar(filtro);

				runOnUiThread(new Runnable() {
					public void run() {

						AdaptadorEspecialista adaptador = new AdaptadorEspecialista(ListaPrestadoresActivity.this, listaDatos);

						setListAdapter(adaptador);

					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ListaPrestadoresActivity.this.progressDialog = ProgressDialog.show(ListaPrestadoresActivity.this, "", "Buscando...", true);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ListaPrestadoresActivity.this.progressDialog.dismiss();
		}

		@Override
		protected void onCancelled(Void result) {
			super.onCancelled(result);
			ListaPrestadoresActivity.this.progressDialog.dismiss();
		}
	}

}
