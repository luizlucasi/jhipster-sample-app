export interface ILivro {
  id?: number;
  isbn?: string;
  titulo?: string;
  assunto?: string;
  editora?: string;
  capaContentType?: string;
  capa?: any;
  numeroPaginas?: number;
  tombo?: number;
  copies?: number;
}

export class Livro implements ILivro {
  constructor(
    public id?: number,
    public isbn?: string,
    public titulo?: string,
    public assunto?: string,
    public editora?: string,
    public capaContentType?: string,
    public capa?: any,
    public numeroPaginas?: number,
    public tombo?: number,
    public copies?: number
  ) {}
}
