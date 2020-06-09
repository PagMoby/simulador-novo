import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimuladorSharedModule } from 'app/shared/shared.module';
import { JurosComponent } from './juros.component';
import { JurosDetailComponent } from './juros-detail.component';
import { JurosUpdateComponent } from './juros-update.component';
import { JurosDeleteDialogComponent } from './juros-delete-dialog.component';
import { jurosRoute } from './juros.route';

@NgModule({
  imports: [SimuladorSharedModule, RouterModule.forChild(jurosRoute)],
  declarations: [JurosComponent, JurosDetailComponent, JurosUpdateComponent, JurosDeleteDialogComponent],
  entryComponents: [JurosDeleteDialogComponent],
})
export class SimuladorJurosModule {}
