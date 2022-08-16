package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclientes.CarroFeignClient;
import com.usuario.service.feignclientes.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repository.UsuarioRepository;


@Service
public class UsuarioService {
    
    @Autowired
    private RestTemplate restTemplate;

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;
    // resttemplate -------------------
    public List<Carro> getCarros(int usuarioId){
        List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMotos(int usuarioId){
        List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
        return motos;
    }
    // resttemplate termino -------------------
    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }
    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }
    
    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        Usuario newusuario = usuarioRepository.save(usuario);
        return newusuario;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){    
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        
        if(usuario == null){
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("Usuario", usuario);
        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        
        if(carros.isEmpty()){
            resultado.put("Carros", "El usuario no tiene carros");
            return resultado;
        }else{
            resultado.put("Carros", carros);
        }
        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()){
            resultado.put("Motos", "El usuario no tiene motos");
            return resultado;
        }else{
            resultado.put("Motos", motos);
        }
        return resultado;
    } 
}