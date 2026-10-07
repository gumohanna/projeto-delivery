package br.com.mohanna.delivery.service;

import br.com.mohanna.delivery.domain.cliente.Cliente;
import br.com.mohanna.delivery.domain.cliente.ClienteRequestDTO;
import br.com.mohanna.delivery.domain.cliente.ClienteResponseDTO;
import br.com.mohanna.delivery.exception.BadRequestException;
import br.com.mohanna.delivery.exception.NotFoundException;
import br.com.mohanna.delivery.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    public void criarCliente(ClienteRequestDTO clienteRequestDTO){
        Optional<Cliente> cliente =  repository.findAll().stream()
                .filter(c -> c.getEmail().equals(clienteRequestDTO.email()))
                .findFirst();
        if (cliente.isEmpty()){
            Cliente novoCliente = Cliente.builder()
                    .nome(clienteRequestDTO.nome())
                    .email(clienteRequestDTO.email())
                    .telefone(clienteRequestDTO.telefone())
                    .endereco(clienteRequestDTO.endereco())
                    .build();
            repository.save(novoCliente);

        } else {
            throw new BadRequestException("Cliente já cadastrado com esse e-mail");
        }
    }

    public List<ClienteResponseDTO> listarTodos(){
        List<Cliente> clientes = repository.findAll();

        return clientes.stream()
                .map(cliente -> new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco()))
                .toList();
    }

    public ClienteResponseDTO listarPorId(Long id){
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco());
    }

    public void atualizarPorId(Long id, ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        cliente.setNome(clienteRequestDTO.nome());
        cliente.setEmail(clienteRequestDTO.email());
        cliente.setTelefone(clienteRequestDTO.telefone());
        cliente.setEndereco(clienteRequestDTO.endereco());

        repository.save(cliente);
    }

    public void deletarPorId(Long id){
        repository.deleteById(id);
    }
}
