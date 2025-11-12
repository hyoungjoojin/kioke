import { type CreatePageRequest, createPage } from '@/app/api/page';
import { type KiokeError } from '@/constant/error';
import { getQueryClient } from '@/lib/query';
import { type Page } from '@/types/page';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = Page;
type TError = KiokeError;
type TVariables = CreatePageRequest;
type TContext = void;

function useCreatePageMutationQuery() {
  const queryClient = getQueryClient();

  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      createPage(requestBody).then((response) => unwrap(response)),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries(
        journalQueryOptions({
          journalId: variables.journalId,
        }),
      );
    },
  });
}

export { useCreatePageMutationQuery };
