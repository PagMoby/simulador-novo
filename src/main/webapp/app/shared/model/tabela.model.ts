import { Operacao } from 'app/shared/model/enumerations/operacao.model';

export interface ITabela {
  operacao?: Operacao;
  valorReceber?: string;
  valorDaVenda?: string;
}

export class Tabela implements ITabela {
  constructor(public operacao?: Operacao, public valorReceber?: string, public valorDaVenda?: string) {}
}
