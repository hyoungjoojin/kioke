import { type CreatePageRequest, createPage } from '@/app/api/page';
import { type Page } from '@/types/page';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = Page;
type TError = KiokeError;
type TVariables = CreatePageRequest;
type TContext = void;

function useCreatePageMutationQuery() {
  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      createPage(requestBody).then((response) => unwrap(response)),
  });
}

export { useCreatePageMutationQuery };
