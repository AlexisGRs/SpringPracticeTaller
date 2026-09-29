package com.codetest.practica.tallermecanico.service;

import com.codetest.practica.tallermecanico.dto.request.MecanicoRequest;
import com.codetest.practica.tallermecanico.dto.response.MecanicoResponse;
import com.codetest.practica.tallermecanico.exception.MecanicoNoEncontradoException;
import com.codetest.practica.tallermecanico.model.Mecanico;
import com.codetest.practica.tallermecanico.repository.MecanicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MecanicoService {
    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    @Transactional
    public MecanicoResponse crearMecanico(MecanicoRequest mecanicoRequest){
        Mecanico mecanico = new Mecanico();
        mecanico.setEspecialidad(mecanicoRequest.getEspecialidad());
        mecanico.setNombre(mecanicoRequest.getNombre());
        mecanico.setActivo(true);

        Mecanico mecanicoGuardado = mecanicoRepository.save(mecanico);
        return toMecanicoResponse(mecanicoGuardado);

    }
    @Transactional(readOnly = true)
    public List<MecanicoResponse> obtenerMecanicos(){
        return mecanicoRepository.findAll().stream().map(this::toMecanicoResponse).toList();
    }

    @Transactional(readOnly = true)
    public MecanicoResponse obtenerMecanicoById(Long id){
        Mecanico mecanico = mecanicoRepository.findById(id).orElseThrow( () -> new MecanicoNoEncontradoException("No existe el mecanico con id:" + id));
        return toMecanicoResponse(mecanico);
    }

    @Transactional
    public MecanicoResponse actualizarMecanico(Long id, MecanicoRequest mecanicoRequest){
        Mecanico mecanico = mecanicoRepository.findById(id).orElseThrow( () -> new MecanicoNoEncontradoException("No existe el mecanico con id:" + id));
        mecanico.setNombre(mecanicoRequest.getNombre());
        mecanico.setEspecialidad(mecanicoRequest.getEspecialidad());

        return toMecanicoResponse(mecanico);
    }

    @Transactional
    public void eliminarMecanico(Long id){
        Mecanico mecanico = mecanicoRepository.findById(id).orElseThrow(() -> new MecanicoNoEncontradoException("No existe el mecanico con  id:" + id));
        mecanicoRepository.delete(mecanico);
    }

    private MecanicoResponse toMecanicoResponse(Mecanico mecanico){
        return new MecanicoResponse(mecanico.getId(), mecanico.getNombre(),mecanico.getEspecialidad(), mecanico.getActivo());
    }


}
