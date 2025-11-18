import { uploadImage } from '@/app/api/image';
import { Button } from '@/components/ui/button';
import { DropdownMenuItem } from '@/components/ui/dropdown-menu';
import Icon from '@/components/ui/icon';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import useUpdateJournalMutation from '@/hooks/query/useUpdateJournalMutation';
import { cn } from '@/lib/utils';
import { getImageDimensions } from '@/util/image';
import { unwrap } from '@/util/result';
import { useClickAway } from '@uidotdev/usehooks';
import Image from 'next/image';
import { useRef, useState } from 'react';
import { toast } from 'sonner';

interface UpdateCoverProps {
  journalId: string;
}

function UpdateCover({ journalId }: UpdateCoverProps) {
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);
  const popoverRef = useClickAway<HTMLDivElement>(() => {
    setIsPopoverOpen(false);
  });

  const [currentFile, setCurrentFile] = useState<{
    file: File | null;
    url: string | null;
  }>({
    file: null,
    url: null,
  });
  const inputRef = useRef<HTMLInputElement | null>(null);

  const { mutate: updateJournal } = useUpdateJournalMutation();

  const fileInputButtonClickHandler = () => {
    if (inputRef.current) {
      inputRef.current.click();
    }
  };

  const fileInputChangeHandler = () => {
    if (!inputRef.current) {
      return;
    }

    const files = inputRef.current.files;
    if (!files || files.length !== 1) {
      return;
    }

    setCurrentFile({
      file: files[0],
      url: URL.createObjectURL(files[0]),
    });
  };

  const uploadButtonClickHandler = async () => {
    if (!currentFile.file || !currentFile.url) {
      toast.error('No file selected.');
      return;
    }

    const { width, height } = await getImageDimensions(currentFile.url);
    const { id } = await uploadImage({
      file: currentFile.file,
      width,
      height,
    }).then((result) => unwrap(result));

    updateJournal({
      path: {
        journalId,
      },
      body: {
        cover: id,
      },
    });
  };

  return (
    <>
      <DropdownMenuItem
        icon='image'
        onSelect={(e) => {
          e.preventDefault();
          setIsPopoverOpen(true);
        }}
      >
        Update Cover
      </DropdownMenuItem>

      <Popover open={isPopoverOpen}>
        <PopoverTrigger
          asChild
          onClick={() => {
            setIsPopoverOpen(true);
          }}
        >
          <span />
        </PopoverTrigger>

        <PopoverContent
          ref={popoverRef}
          side='right'
          className='mx-1 w-96 h-72 flex flex-col gap-4'
        >
          <div>
            <div
              className={cn(
                'whitespace-nowrap py-10 px-8 border-2 border-dashed border-card-muted rounded-xl text-center cursor-pointer',
                'hover:border-primary transition-all duration-300',
                'relative h-full grow',
              )}
              onClick={fileInputButtonClickHandler}
            >
              {currentFile.url ? (
                <Image src={currentFile.url} alt='' fill />
              ) : (
                <div className='space-y-3'>
                  <div className='flex justify-center'>
                    <div className='w-16 h-16 rounded-full bg-gradient-to-tr from-purple-100 to-pink-100 flex items-center justify-center group-hover:scale-110 transition-transform duration-300'>
                      <span className='text-3xl'>
                        <Icon name='upload' />
                      </span>
                    </div>
                  </div>
                  <div className='space-y-1'>
                    <p className='text-sm font-semibold text-gray-700'>
                      Drop your file here or click to browse
                    </p>
                  </div>
                </div>
              )}
            </div>
            <input
              ref={inputRef}
              type='file'
              className='hidden'
              onChange={fileInputChangeHandler}
            />
          </div>

          <div className='flex gap-2 pt-2'>
            <Button
              variant='ghost'
              className='flex-1 px-4 py-2.5 rounded-lg border border-gray-300 text-sm font-medium '
              onClick={() => setIsPopoverOpen(false)}
            >
              Cancel
            </Button>
            <Button
              className='flex-1 px-4 py-2.5 rounded-lg text-white text-sm font-medium'
              onClick={uploadButtonClickHandler}
            >
              Upload
            </Button>
          </div>
        </PopoverContent>
      </Popover>
    </>
  );
}

export default UpdateCover;
