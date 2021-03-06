import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJuros, Juros } from 'app/shared/model/juros.model';
import { JurosService } from './juros.service';
import { JurosComponent } from './juros.component';
import { JurosDetailComponent } from './juros-detail.component';
import { JurosUpdateComponent } from './juros-update.component';
import { SimuladorComponent } from './simulador.component';

@Injectable({ providedIn: 'root' })
export class JurosResolve implements Resolve<IJuros> {
  constructor(private service: JurosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJuros> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((juros: HttpResponse<Juros>) => {
          if (juros.body) {
            return of(juros.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Juros());
  }
}

export const jurosRoute: Routes = [
  {
    path: '',
    component: JurosComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'simuladorApp.juros.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'simular',
    component: SimuladorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simuladorApp.juros.simulador.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JurosDetailComponent,
    resolve: {
      juros: JurosResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'simuladorApp.juros.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JurosUpdateComponent,
    resolve: {
      juros: JurosResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'simuladorApp.juros.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JurosUpdateComponent,
    resolve: {
      juros: JurosResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'simuladorApp.juros.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
