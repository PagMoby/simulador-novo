import { Operacao } from 'app/shared/model/enumerations/operacao.model';

export interface IJuros {
  id?: number;
  juros?: number;
  operacao?: Operacao;
  planoNome?: string;
  planoId?: number;
}

export class Juros implements IJuros {
  constructor(public id?: number, public juros?: number, public operacao?: Operacao, public planoNome?: string, public planoId?: number) {}
}
