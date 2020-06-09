import { IJuros } from 'app/shared/model/juros.model';
import { ISeller } from 'app/shared/model/seller.model';

export interface IPlano {
  id?: number;
  nome?: string;
  juros?: IJuros[];
  sellers?: ISeller[];
}

export class Plano implements IPlano {
  constructor(public id?: number, public nome?: string, public juros?: IJuros[], public sellers?: ISeller[]) {}
}
