export enum ErrorCode {
  INTERNAL_SERVER_ERROR = 'INTERNAL_SERVER_ERROR',
  BAD_REQUEST = 'BAD_REQUEST',
  UNAUTHENTICATED = 'UNAUTHENTICATED',
  ACCESS_DENIED = 'ACCESS_DENIED',
  USER_NOT_FOUND = 'USER_NOT_FOUND',
  USER_ALREADY_EXISTS = 'USER_ALREADY_EXISTS',
  BAD_CREDENTIALS = 'BAD_CREDENTIALS',
  JOURNAL_NOT_FOUND = 'JOURNAL_NOT_FOUND',
  COLLECTION_NOT_FOUND = 'COLLECTION_NOT_FOUND',
  PAGE_NOT_FOUND = 'PAGE_NOT_FOUND',

  NETWORK_REQUEST_FAILED = 'NETWORK_REQUEST_FAILED',
  UNKNOWN_ERROR = 'UNKNOWN_ERROR',
  JSON_PARSE_FAILED = 'JSON_PARSE_FAILED',
}

// export function getToastMessageKey(
//   error: unknown,
//   handlers?: Record<ErrorCode, string>,
// ) {
//   if (!error || !(error instanceof Error)) {
//     return 'error.unknown-error';
//   }
//
//   if (error instanceof KiokeError) {
//     // TODO: Use the custom handlers to override default settings
//
//     switch (
//       error.code
//       // TODO: Map error codes to toast messages
//     ) {
//     }
//   }
//
//   return 'error.unknown-error';
// }
//
// function handleKiokeError(
//   error: unknown,
//   defaultHandlers: Partial<Record<ErrorCode, () => any>>,
//   handlers?: Partial<Record<ErrorCode, () => any>>,
// ) {
//   if (!error) {
//     return;
//   }
//
//   if (!(error instanceof Error) || !(error instanceof KiokeError)) {
//     throw error;
//   }
//
//   if (error.code === ErrorCode.INTERNAL_SERVER_ERROR) {
//     logger.error(error);
//   } else {
//     logger.debug(error);
//   }
//
//   const handler = handlers?.[error.code];
//   if (handler) {
//     return handler();
//   }
//
//   const defaultHandler = defaultHandlers?.[error.code];
//   if (defaultHandler) {
//     return defaultHandler();
//   }
// }
//
// export function handleServerSideKiokeError(
//   error: unknown,
//   handlers?: Partial<Record<ErrorCode, () => any>>,
// ) {
//   return handleKiokeError(
//     error,
//     {
//       [ErrorCode.ACCESS_DENIED]: () => {
//         redirect(Routes.SIGN_IN);
//       },
//       [ErrorCode.PAGE_NOT_FOUND]: () => {
//         notFound();
//       },
//     },
//     handlers,
//   );
// }
//
// export function handleClientSideKiokeError(
//   error: unknown,
//   handlers?: Partial<Record<ErrorCode, () => any>>,
// ) {
//   return handleKiokeError(
//     error,
//     {
//       [ErrorCode.ACCESS_DENIED]: () => {
//         redirect(Routes.SIGN_IN);
//       },
//     },
//     handlers,
//   );
// }
