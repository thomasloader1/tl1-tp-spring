package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidad.Bicicleta;
import com.tallerwebi.dominio.entidad.Resena;
import com.tallerwebi.dominio.excepcion.ResenaPuntajeValidacion;
import com.tallerwebi.dominio.excepcion.ResenaValidacion;
import com.tallerwebi.infraestructura.repositorios.RepositorioBicicleta;
import com.tallerwebi.infraestructura.repositorios.RepositorioResena;
import com.tallerwebi.presentacion.dto.DatosResena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.List;

@Service("servicioResena")
@Transactional
public class ServicioResenaImpl implements ServicioResena {
    private final RepositorioResena repositorioResena;
    private final RepositorioBicicleta repositorioBicicleta;

    @Autowired
    public ServicioResenaImpl(RepositorioResena repositorioResena, RepositorioBicicleta repositorioBicicleta) {
        this.repositorioResena = repositorioResena;
        this.repositorioBicicleta = repositorioBicicleta;
    }

    @Override
    public void subirResena(DatosResena datosResena) throws ResenaValidacion, ResenaPuntajeValidacion {
        if (datosResena.getComentario().isEmpty() || datosResena.getPuntaje() == null) {
            throw new ResenaValidacion();
        }
        if (datosResena.getPuntaje() < 1 || datosResena.getPuntaje() > 5) {
            throw new ResenaPuntajeValidacion();
        }
        Bicicleta bicicleta = datosResena.getBicicleta();
        bicicleta.setPuntaje(bicicleta.getPuntaje() + datosResena.getPuntaje());

        Resena resena = new Resena(datosResena.getPuntaje(), datosResena.getComentario(), datosResena.getBicicleta(), datosResena.getUsuario());

        repositorioResena.subirResena(resena);
        repositorioBicicleta.actualizarPuntajeBici(bicicleta);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaBicicleta(Bicicleta bicicleta) {
        return repositorioResena.obtenerResenasDeUnaBicicleta(bicicleta);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaClientePorId(Long id) {
        return repositorioResena.obtenerResenasDeUnaClientePorId(id);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaClientePorIdPuntajeBueno(Long id) {
        return repositorioResena.obtenerResenasDeUnaClientePorIdPuntajeBueno(id);

    }

    @Override
    public List<Resena> obtenerResenasDeUnaClientePorIdPuntajeRegular(Long id) {
        return repositorioResena.obtenerResenasDeUnaClientePorIdPuntajeRegular(id);
    }

    @Override
    public List<Resena> obtenerResenasDeUnaClientePorIdPuntajeMalo(Long id) {
        return repositorioResena.obtenerResenasDeUnaClientePorIdPuntajeMalo(id);
    }

    @Override
    public int calcularPuntaje(Bicicleta bicicleta) {
        int cantResenas = repositorioResena.obtenerCantidadDeResenasParaUnaBicicleta(bicicleta);
        int puntajeTotal = bicicleta.getPuntaje();

        if(cantResenas == 0){
            cantResenas = 1;
        }

        return (int) Math.round(this.toTwoDecimals(puntajeTotal / cantResenas));
    }

    private double toTwoDecimals(double number) {
        return Double.parseDouble(new DecimalFormat("#.0").format(number).replace(',', '.'));
    }
}
