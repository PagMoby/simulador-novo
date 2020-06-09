export interface ISeller {
  id?: number;
  email?: string;
  planoNome?: string;
  planoId?: number;
}

export class Seller implements ISeller {
  constructor(public id?: number, public email?: string, public planoNome?: string, public planoId?: number) {}
}
