import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'seller',
        loadChildren: () => import('./seller/seller.module').then(m => m.SimuladorSellerModule),
      },
      {
        path: 'plano',
        loadChildren: () => import('./plano/plano.module').then(m => m.SimuladorPlanoModule),
      },
      {
        path: 'juros',
        loadChildren: () => import('./juros/juros.module').then(m => m.SimuladorJurosModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SimuladorEntityModule {}
