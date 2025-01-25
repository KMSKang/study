import { deletePost } from '@/api';
import queryClient from '@/api/queryClient';
import { queryKeys } from '@/constants';
import { UseMutationCustomOptions } from '@/types';
import { useMutation } from '@tanstack/react-query';

function useMutateDeletePost(mutationOptions?: UseMutationCustomOptions) {
    return useMutation({
        mutationFn: deletePost,
        onSuccess: deleteId => {
            queryClient.invalidateQueries({
                queryKey: [queryKeys.POST, queryKeys.GET_POSTS],
            });
            queryClient.invalidateQueries({
                queryKey: [queryKeys.MARKER, queryKeys.GET_MARKERS],
            });
            queryClient.invalidateQueries({
                queryKey: [queryKeys.POST, queryKeys.GET_CALENDAR_POSTS],
            });
        },
        ...mutationOptions,
    });
}

export default useMutateDeletePost;