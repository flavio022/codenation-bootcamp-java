package br.com.codenation;

import br.com.codenation.entities.Jogador;
import br.com.codenation.entities.Time;
import br.com.codenation.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.exceptions.TimeNaoEncontradoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class DesafioMeuTimeApplication implements MeuTimeInterface {
	private final List<Time> times = new ArrayList<>();
	private final List<Jogador> jogadores = new ArrayList<>();

	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if (times.stream().anyMatch(time -> time.getId().equals(id)))
			throw new IdentificadorUtilizadoException();

		times.add(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}

	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		if (jogadores.stream().anyMatch(jogador -> jogador.getId().equals(id)))
			throw new IdentificadorUtilizadoException();
		validarExistenciaTime(idTime);

		jogadores.add(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
	}

	public void definirCapitao(Long idJogador) {
		if (jogadores.stream().noneMatch(jogador -> jogador.getId().equals(idJogador)))
			throw new JogadorNaoEncontradoException();

		jogadores.forEach(jogador -> jogador.setCapitao(jogador.getId().equals(idJogador)));
	}

	public Long buscarCapitaoDoTime(Long idTime) {
		Time time = buscarTimePorId(idTime);

		Jogador capitao = jogadores.stream()
				.filter(jogador -> jogador.getIdTime().equals(time.getId()) && jogador.getCapitao())
				.findFirst()
				.orElseThrow(CapitaoNaoInformadoException::new);

		return capitao.getId();
	}

	public String buscarNomeJogador(Long idJogador) {
		return buscarJogadorPorId(idJogador).getNome();
	}

	public String buscarNomeTime(Long idTime) {
		return buscarTimePorId(idTime).getNome();
	}

	public List<Long> buscarJogadoresDoTime(Long idTime) {
		validarExistenciaTime(idTime);

		return jogadores.stream()
				.filter(jogador -> jogador.getIdTime().equals(idTime))
				.map(Jogador::getId)
				.collect(Collectors.toList());
	}

	public Long buscarMelhorJogadorDoTime(Long idTime) {
		validarExistenciaTime(idTime);

		return jogadores.stream()
				.filter(j -> j.getIdTime().equals(idTime))
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade).reversed().thenComparingLong(Jogador::getId))
				.map(Jogador::getId)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	public Long buscarJogadorMaisVelho(Long idTime) {
		validarExistenciaTime(idTime);

		return jogadores.stream()
				.filter(j -> j.getIdTime().equals(idTime))
				.sorted(Comparator.comparing(Jogador::getDataNascimento).thenComparingLong(Jogador::getId))
				.map(Jogador::getId)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	public List<Long> buscarTimes() {
		return times.stream()
				.sorted(Comparator.comparingLong(Time::getId))
				.map(Time::getId)
				.collect(Collectors.toList());
	}

	public Long buscarJogadorMaiorSalario(Long idTime) {
		validarExistenciaTime(idTime);

		return jogadores.stream()
				.filter(j -> j.getIdTime().equals(idTime))
				.sorted(Comparator.comparing(Jogador::getSalario).reversed().thenComparingLong(Jogador::getId))
				.map(Jogador::getId)
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return buscarJogadorPorId(idJogador).getSalario();
	}

	public List<Long> buscarTopJogadores(Integer top) {
		return jogadores.stream()
				.sorted(Comparator.comparingInt(Jogador::getNivelHabilidade).reversed().thenComparingLong(Jogador::getId))
				.limit(top)
				.map(Jogador::getId)
				.collect(Collectors.toList());
	}

	private void validarExistenciaTime(Long idTime) {
		if (times.stream().noneMatch(time -> time.getId().equals(idTime)))
			throw new TimeNaoEncontradoException();
	}

	private Jogador buscarJogadorPorId(Long idJogador) {
		return jogadores.stream()
				.filter(j -> j.getId().equals(idJogador))
				.findFirst()
				.orElseThrow(JogadorNaoEncontradoException::new);
	}

	private Time buscarTimePorId(Long idTime) {
		return times.stream()
				.filter(t -> t.getId().equals(idTime))
				.findFirst()
				.orElseThrow(TimeNaoEncontradoException::new);
	}

}
