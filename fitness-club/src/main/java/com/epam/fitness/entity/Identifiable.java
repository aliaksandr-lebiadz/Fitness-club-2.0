package com.epam.fitness.entity;

/**
 * <p>Any class, which implements {@link Identifiable} interface shows,
 * that an instance of this class can be stored in any storage.</p>
 *
 * <p>Each instance should have a unique id of the {@link Integer} type.
 * The id of the instance may be null, if this instance isn't
 * stored in the storage.</p>
 */
public interface Identifiable {

    Integer getId();

}