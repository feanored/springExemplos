import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FuncionarioCadastroComponent } from './funcionario.cadastro.component';

describe('FuncionarioCadastroComponent', () => {
  let component: FuncionarioCadastroComponent;
  let fixture: ComponentFixture<FuncionarioCadastroComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FuncionarioCadastroComponent]
    });
    fixture = TestBed.createComponent(FuncionarioCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
