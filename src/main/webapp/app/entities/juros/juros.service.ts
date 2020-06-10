import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJuros } from 'app/shared/model/juros.model';
import { ITabela } from 'app/shared/model/tabela.model';

type EntityResponseType = HttpResponse<IJuros>;
type EntityArrayResponseType = HttpResponse<IJuros[]>;

@Injectable({ providedIn: 'root' })
export class JurosService {
  public resourceUrl = SERVER_API_URL + 'api/juros';

  constructor(protected http: HttpClient) {}

  create(juros: IJuros): Observable<EntityResponseType> {
    return this.http.post<IJuros>(this.resourceUrl, juros, { observe: 'response' });
  }

  update(juros: IJuros): Observable<EntityResponseType> {
    return this.http.put<IJuros>(this.resourceUrl, juros, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJuros>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJuros[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  simular(valor: number, semJuros: string): Observable<EntityArrayResponseType> {
    return this.http.post<ITabela[]>(`${this.resourceUrl}/simular/${valor}/${semJuros}`, null, { observe: 'response' });
  }
}
