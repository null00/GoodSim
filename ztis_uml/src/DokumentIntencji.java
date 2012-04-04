import java.util.List;

public class DokumentIntencji {
	private int idKonsumenta;
	private List<Dobro> dobroIn;
	private List<Dobro> dobroOut;

	public DokumentIntencji() {
		super();
	}

	public DokumentIntencji(int idKonsumenta, List<Dobro> dobroIn,
			List<Dobro> dobroOut) {
		super();
		this.idKonsumenta = idKonsumenta;
		this.dobroIn = dobroIn;
		this.dobroOut = dobroOut;
	}

	public int getIdKonsumenta() {
		return idKonsumenta;
	}

	public void setIdKonsumenta(int idKonsumenta) {
		this.idKonsumenta = idKonsumenta;
	}

	public List<Dobro> getDobroIn() {
		return dobroIn;
	}

	public void setDobroIn(List<Dobro> dobroIn) {
		this.dobroIn = dobroIn;
	}

	public List<Dobro> getDobroOut() {
		return dobroOut;
	}

	public void setDobroOut(List<Dobro> dobroOut) {
		this.dobroOut = dobroOut;
	}

}
