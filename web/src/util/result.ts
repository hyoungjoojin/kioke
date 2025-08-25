import type { Result } from 'neverthrow';

export function unwrap<T, E>(result: Result<T, E>): T {
  if (result.isErr()) {
    throw result.error;
  }

  return result.value;
}
