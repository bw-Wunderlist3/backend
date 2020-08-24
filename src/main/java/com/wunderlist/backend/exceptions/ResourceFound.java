package com.wunderlist.backend.exceptions;

    public class ResourceFound extends RuntimeException
    {
        public ResourceFound(String message)
        {
            super("Error from Wonderlist " + message);
        }
    }
