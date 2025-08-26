import { type UpdatePageRequest, updatePage } from '@/app/api/page';
import type KiokeError from '@/util/error';
import { unwrap } from '@/util/result';
import { useMutation } from '@tanstack/react-query';

type TData = void;
type TError = KiokeError;
type TVariables = UpdatePageRequest;
type TContext = void;

type QueryParams = {
  id: string;
};

function useUpdatePageMutation(params: QueryParams) {
  return useMutation<TData, TError, TVariables, TContext>({
    mutationFn: async (requestBody) =>
      updatePage(params, requestBody).then((response) => unwrap(response)),
  });
}

export { useUpdatePageMutation };
