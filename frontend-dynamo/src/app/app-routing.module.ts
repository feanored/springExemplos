import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FuncionarioCadastroComponent } from './funcionario.cadastro/funcionario.cadastro.component';

const routes: Routes = [
  { path: 'cadastro', component: FuncionarioCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
