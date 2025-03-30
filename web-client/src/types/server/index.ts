export interface HttpResponseBody<T> {
  requestId: string;
  success: boolean;
  path: string;
  status: string;
  timestamp: string;
  data?: T;
  error?: ErrorDetail;
}

export interface ErrorDetail {
  code: string;
  title: string;
  message: string;
  details?: string;
}
