export type ProblemDetail = {
  title?: string;
  type?: string;
  status?: number;
  details?: string;
  code?: string;
};

export type PagedModel = {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
};
