package com.franco.dev;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.franco.dev.domain.Categoria;
import com.franco.dev.domain.Cidade;
import com.franco.dev.domain.Cliente;
import com.franco.dev.domain.Endereco;
import com.franco.dev.domain.Estado;
import com.franco.dev.domain.ItemPedido;
import com.franco.dev.domain.Pagamento;
import com.franco.dev.domain.PagamentoComBoleto;
import com.franco.dev.domain.PagamentoComCartao;
import com.franco.dev.domain.Pedido;
import com.franco.dev.domain.Produto;
import com.franco.dev.enums.EstadoPagamento;
import com.franco.dev.enums.TipoCliente;
import com.franco.dev.repository.CategoriaRepository;
import com.franco.dev.repository.CidadeRepository;
import com.franco.dev.repository.ClienteRepository;
import com.franco.dev.repository.EnderecoRepository;
import com.franco.dev.repository.EstadoRepository;
import com.franco.dev.repository.ItemPedidoRepository;
import com.franco.dev.repository.PagamentoRepository;
import com.franco.dev.repository.PedidoRepository;
import com.franco.dev.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired 
	EnderecoRepository enderecoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired 
	PagamentoRepository pagamentoRepository;
	
	@Autowired 
	ItemPedidoRepository itemPedidoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 =  new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "07804831110", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null, "Av. Paraguay", "123", "Bodega Franco", "San Miguel", "7200", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Bragaça", "630", "Apto. 302", "Zona 7", "0987", cli1, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 19:35"), cli1, e2);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"), null);
		ped2.setPagamento(pagto2);
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2,c3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p1.getItens().addAll(Arrays.asList(ip1));
		
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getItens().addAll(Arrays.asList(ip3));
		
		p3.getCategorias().add(cat1);
		p3.getItens().addAll(Arrays.asList(ip2));
		
		cli1.getTelefones().addAll(Arrays.asList("44998341377", "0983123456"));
		
		cli1.setEnderecos(Arrays.asList(e1,e2));
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
