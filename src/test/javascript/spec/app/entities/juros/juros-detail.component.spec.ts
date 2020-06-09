import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimuladorTestModule } from '../../../test.module';
import { JurosDetailComponent } from 'app/entities/juros/juros-detail.component';
import { Juros } from 'app/shared/model/juros.model';

describe('Component Tests', () => {
  describe('Juros Management Detail Component', () => {
    let comp: JurosDetailComponent;
    let fixture: ComponentFixture<JurosDetailComponent>;
    const route = ({ data: of({ juros: new Juros(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimuladorTestModule],
        declarations: [JurosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JurosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JurosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load juros on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.juros).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
