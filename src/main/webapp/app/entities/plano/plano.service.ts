import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlano } from 'app/shared/model/plano.model';

type EntityResponseType = HttpResponse<IPlano>;
type EntityArrayResponseType = HttpResponse<IPlano[]>;

@Injectable({ providedIn: 'root' })
export class PlanoService {
  public resourceUrl = SERVER_API_URL + 'api/planos';

  constructor(protected http: HttpClient) {}

  create(plano: IPlano): Observable<EntityResponseType> {
    return this.http.post<IPlano>(this.resourceUrl, plano, { observe: 'response' });
  }

  update(plano: IPlano): Observable<EntityResponseType> {
    return this.http.put<IPlano>(this.resourceUrl, plano, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlano>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlano[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
